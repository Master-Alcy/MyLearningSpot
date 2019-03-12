select * from 
(
SELECT t.*,WorkflowCode as WorkflowStatus,StatusCode as TradeStatus,BOOK_NAME AS Book,TradeTypeName as TradeType,TicketTypeName as TicketType,Login as Trader,c1.CptyExternalId as Counterparty,c1.CptyShortName as CptyShortName,c2.CptyExternalId as Counterparty2,c2.CptyShortName as CptyShortName2,c.CptyExternalId as OriginatingParty,c.CptyShortName as OriginatingPartyShortName FROM
(
	SELECT t2.* from
	(
		select distinct tradeid from TS_TRADE_HISTORY with(NOLOCK)
	) t1
	CROSS APPLY
	(
		SELECT TOP 1 * from TS_TRADE_HISTORY with(NOLOCK)
		where t1.tradeid = tradeid and versionts < '3/9/19 12:00 AM'
		ORDER BY tradehistoryid desc
	) t2
) t  
JOIN TS_WORKFLOW_STATUS with(NOLOCK) on TS_WORKFLOW_STATUS.WorkflowStatusId = t.workflowstatusid
JOIN TS_TRADE_STATUS with(nolock) on TS_TRADE_STATUS.TradeStatusId = t.TradeStatusId JOIN BOOK with(nolock) ON BOOK.BOOK_ID = t.BookId 
JOIN TS_TRADE_TYPE with(nolock) ON TS_TRADE_TYPE.TradeTypeId = t.TradeTypeId JOIN TS_TICKET_TYPE with(nolock) ON TS_TICKET_TYPE.TicketTypeId = t.TicketTypeId 
JOIN TRADER with(nolock) ON TRADER.TRADER_ID = t.TraderId JOIN TS_COUNTERPARTY c1 with(nolock) on c1.CounterpartyId = t.CounterpartyId 
LEFT JOIN TS_COUNTERPARTY c2 with(nolock) on c2.CounterpartyId = t.CounterpartyId2
LEFT JOIN TS_COUNTERPARTY c with(nolock) on c.CounterpartyId = t.OriginatingPartyId 
WHERE 1=1
AND WorkflowCode IN ('ACCEPTED','AMENDED','CONFIRM_ACCEPTED','CONFIRM_AMENDED')
AND t.bookId in
	(
	select book_id from trader_book_rel WITH (NOLOCK)
	join Trader WITH (NOLOCK) on trader_book_rel.trader_id = trader.trader_id where trader.login='lmrtrader' AND trader_book_rel.Readable='true'
	)
) t
OUTER APPLY (select * from TS_BOND_SECURITY_HISTORY s WITH (NOLOCK) where t.SecurityId=s.SecurityId and t.SecurityVersion=s.Version and t.TradeType='bond') bond 
OUTER APPLY (select * from TS_FUTURE_SECURITY_HISTORY s WITH (NOLOCK) where t.SecurityId=s.SecurityId and t.SecurityVersion=s.Version and t.TradeType='future') future 
OUTER APPLY (select * from TS_FUTURE_OPTION_SECURITY_HISTORY s WITH (NOLOCK) where t.SecurityId=s.SecurityId and t.SecurityVersion=s.Version and t.TradeType='futureoption') futureoption 
OUTER APPLY (select * from TS_IRSWAP_SECURITY_HISTORY s WITH (NOLOCK) where t.SecurityId=s.SecurityId and t.SecurityVersion=s.Version and t.TradeType='irswap') irswap 
OUTER APPLY (select * from TS_BASIS_SWAP_SECURITY_HISTORY s WITH (NOLOCK) where t.SecurityId=s.SecurityId and t.SecurityVersion=s.Version and t.TradeType='irbasisswap') irbasisswap 
OUTER APPLY (select * from TS_FRA_SECURITY_HISTORY s WITH (NOLOCK) where t.SecurityId=s.SecurityId and t.SecurityVersion=s.Version and t.TradeType='fra') fra 
OUTER APPLY (select * from TS_SWAPTION_SECURITY_HISTORY s WITH (NOLOCK) where t.SecurityId=s.SecurityId and t.SecurityVersion=s.Version and t.TradeType='swaption') swaption 
OUTER APPLY (select * from TS_GENERIC_OPTION_SECURITY_HISTORY s WITH (NOLOCK) where t.SecurityId=s.SecurityId and t.SecurityVersion=s.Version and t.TradeType='genericoption') genericoption 
OUTER APPLY (select * from TS_GENERIC_FUTURE_SECURITY_HISTORY s WITH (NOLOCK) where t.SecurityId=s.SecurityId and t.SecurityVersion=s.Version and t.TradeType='genericfuture') genericfuture 
OUTER APPLY (select * from TS_PAYMENT_SECURITY_HISTORY s WITH (NOLOCK) where t.SecurityId=s.SecurityId and t.SecurityVersion=s.Version and t.TradeType='payment') payment 
OUTER APPLY (select * from TS_CROSS_CCY_SWAP_SECURITY_HISTORY s WITH (NOLOCK) where t.SecurityId=s.SecurityId and t.SecurityVersion=s.Version and t.TradeType='crosscurrencyswap') crosscurrencyswap 
OUTER APPLY (select * from TS_CAP_FLOOR_SECURITY_HISTORY s WITH (NOLOCK) where t.SecurityId=s.SecurityId and t.SecurityVersion=s.Version and t.TradeType='capfloor') capfloor
OUTER APPLY (select * from TS_FXFWD_SECURITY_HISTORY s WITH (NOLOCK) where t.SecurityId=s.SecurityId and t.SecurityVersion=s.Version and t.TradeType='fxfwd') fxfwd 
OUTER APPLY (select * from TS_TBA_SECURITY_HISTORY s WITH (NOLOCK) where t.SecurityId=s.SecurityId and t.SecurityVersion=s.Version and t.TradeType='tba') tba 
OUTER APPLY (select * from TS_CMO_SECURITY_HISTORY s WITH (NOLOCK) where t.SecurityId=s.SecurityId and t.SecurityVersion=s.Version and t.TradeType='cmo') cmo 
OUTER APPLY (select * from TS_MBS_SECURITY_HISTORY s WITH (NOLOCK) where t.SecurityId=s.SecurityId and t.SecurityVersion=s.Version and t.TradeType='mbs') mbs 
OUTER APPLY (select * from TS_CMSWAP_SPREAD_OPTION_SECURITY_HISTORY s WITH (NOLOCK) where t.SecurityId=s.SecurityId and t.SecurityVersion=s.Version and t.TradeType='cmswapsprdopt') cmswapsprdopt