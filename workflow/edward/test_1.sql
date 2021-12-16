
(with details as (
    select sb_timezone(ut.inserted_at, ut.city_id) :: date          as date,
           sb_timezone(ut.inserted_at, ut.city_id)     as timestamps,
           ut.user_id,
           u.first_name || ' ' || u.last_name                                   as vendor_name,
           u.phone_number,
           case when ut.transaction_type = 'debit' then -amount else amount end as amount,
           case
               when lower(trim(ut.source)) = 'food' and food_delivery_order_id isnull
                        and customer.user_type = 'passenger' then 'customer offline payment'
               when lower(trim(ut.source)) = 'food' and  food_delivery_order_id isnull and
                    driver.user_type = 'driver' then 'driver offline payment'
               when lower(trim(ut.source)) = 'food' and lower(trim(ut.reason)) like '%commission for%' then 'commission'
               when lower(trim(ut.source)) = 'food' and lower(trim(ut.reason)) like '%food for%' then 'food'
               else lower(ut.reason) end                                        as reason,
           ut.transaction_type,
           balance
    from user_transaction ut
             left join "user" u on ut.user_id = u.id
             left join "user" customer on customer.id = ut.sender_user_id
             left join "user" driver on driver.id = ut.sender_user_id
    where u.user_type in ('agent', 'vendor')
      and ut.inserted_at BETWEEN {{startdate}}::date - INTERVAL '1' DAY AND {{enddate}}::date + INTERVAL '1' DAY --OPTIMIZATION
      and sb_timezone(ut.inserted_at, ut.city_id) :: date between {{startdate}}::date and {{enddate}}::date
      and ut.status = 'completed'
      --and ut.user_id = 2136368
)
select *
    from (
             (select date,
                     txn_id,
                     user_id                as partner_id,
                     reason,
                     amount,
                     wallet_flow,
                     balance                as current_balance,
                     prev_balance,
                     (amount - wallet_flow) as missing_amount
              from (
                       select *,
                              case
                                  when abs_amount <> abs(wallet_flow) and abs(abs_amount - abs(wallet_flow)) > 0 then 1
                                  else 0 end as status
                       from (
                                select *,
                                       (balance - prev_balance) as wallet_flow,
                                       abs(amount)              as abs_amount
                                from (SELECT date,
                                             txn_id,
                                             timestamps,
                                             user_id,
                                             reason,
                                             amount,
                                             balance,
                                             done_by,
                                             lag(amount) over (partition by user_id order by txn_id )      as prev_amount,
                                             lag(balance) over (partition by user_id order by txn_id )     as prev_balance,
                                             row_number() over (partition by user_id order by txn_id asc ) as rn
                                      FROM details) as sub
                            ) sub_1
                   ) as sub_0
              where sub_0.status = 1
             )
         ) as sub
);
