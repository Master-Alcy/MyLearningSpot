SELECT *
FROM
	(
SELECT t.*, WorkflowCode AS WorkflowStatus, StatusCode AS TradeStatus, BOOK_NAME AS Book, TradeTypeName AS TradeType, TicketTypeName AS TicketType, Login AS Trader, c1.CptyExternalId AS Counterparty, c1.CptyShortName AS CptyShortName, c2.CptyExternalId AS Counterparty2, c2.CptyShortName AS CptyShortName2, c.CptyExternalId AS OriginatingParty, c.CptyShortName AS OriginatingPartyShortName
	FROM
		(
	SELECT t2.*
		FROM
			(
		SELECT DISTINCT tradeid
			FROM TS_TRADE_HISTORY WITH(NOLOCK)
	) t1
	CROSS APPLY
	(
		SELECT TOP 1
				*
			FROM TS_TRADE_HISTORY WITH(NOLOCK)
			WHERE t1.tradeid = tradeid AND versionts < '3/9/19 12:00 AM'
			ORDER BY tradehistoryid DESC
	) t2
) t
		JOIN TS_WORKFLOW_STATUS WITH(NOLOCK) ON TS_WORKFLOW_STATUS.WorkflowStatusId = t.workflowstatusid
		JOIN TS_TRADE_STATUS WITH(NOLOCK) ON TS_TRADE_STATUS.TradeStatusId = t.TradeStatusId JOIN BOOK WITH(NOLOCK) ON BOOK.BOOK_ID = t.BookId
		JOIN TS_TRADE_TYPE WITH(NOLOCK) ON TS_TRADE_TYPE.TradeTypeId = t.TradeTypeId JOIN TS_TICKET_TYPE WITH(NOLOCK) ON TS_TICKET_TYPE.TicketTypeId = t.TicketTypeId
		JOIN TRADER WITH(NOLOCK) ON TRADER.TRADER_ID = t.TraderId JOIN TS_COUNTERPARTY c1 WITH(NOLOCK) ON c1.CounterpartyId = t.CounterpartyId
		LEFT JOIN TS_COUNTERPARTY c2 WITH(NOLOCK) ON c2.CounterpartyId = t.CounterpartyId2
		LEFT JOIN TS_COUNTERPARTY c WITH(NOLOCK) ON c.CounterpartyId = t.OriginatingPartyId
	WHERE 1=1
		AND WorkflowCode IN ('ACCEPTED','AMENDED','CONFIRM_ACCEPTED','CONFIRM_AMENDED')
		AND t.bookId IN
	(
	SELECT book_id
		FROM trader_book_rel WITH (NOLOCK)
			JOIN Trader WITH (NOLOCK) ON trader_book_rel.trader_id = trader.trader_id
		WHERE trader.login='lmrtrader' AND trader_book_rel.Readable='true'
	)
) t
OUTER APPLY (SELECT *
	FROM TS_BOND_SECURITY_HISTORY s WITH (NOLOCK)
	WHERE t.SecurityId=s.SecurityId AND t.SecurityVersion=s.Version AND t.TradeType='bond') bond 
OUTER APPLY (SELECT *
	FROM TS_FUTURE_SECURITY_HISTORY s WITH (NOLOCK)
	WHERE t.SecurityId=s.SecurityId AND t.SecurityVersion=s.Version AND t.TradeType='future') future 
OUTER APPLY (SELECT *
	FROM TS_FUTURE_OPTION_SECURITY_HISTORY s WITH (NOLOCK)
	WHERE t.SecurityId=s.SecurityId AND t.SecurityVersion=s.Version AND t.TradeType='futureoption') futureoption 
OUTER APPLY (SELECT *
	FROM TS_IRSWAP_SECURITY_HISTORY s WITH (NOLOCK)
	WHERE t.SecurityId=s.SecurityId AND t.SecurityVersion=s.Version AND t.TradeType='irswap') irswap 
OUTER APPLY (SELECT *
	FROM TS_BASIS_SWAP_SECURITY_HISTORY s WITH (NOLOCK)
	WHERE t.SecurityId=s.SecurityId AND t.SecurityVersion=s.Version AND t.TradeType='irbasisswap') irbasisswap 
OUTER APPLY (SELECT *
	FROM TS_FRA_SECURITY_HISTORY s WITH (NOLOCK)
	WHERE t.SecurityId=s.SecurityId AND t.SecurityVersion=s.Version AND t.TradeType='fra') fra 
OUTER APPLY (SELECT *
	FROM TS_SWAPTION_SECURITY_HISTORY s WITH (NOLOCK)
	WHERE t.SecurityId=s.SecurityId AND t.SecurityVersion=s.Version AND t.TradeType='swaption') swaption 
OUTER APPLY (SELECT *
	FROM TS_GENERIC_OPTION_SECURITY_HISTORY s WITH (NOLOCK)
	WHERE t.SecurityId=s.SecurityId AND t.SecurityVersion=s.Version AND t.TradeType='genericoption') genericoption 
OUTER APPLY (SELECT *
	FROM TS_GENERIC_FUTURE_SECURITY_HISTORY s WITH (NOLOCK)
	WHERE t.SecurityId=s.SecurityId AND t.SecurityVersion=s.Version AND t.TradeType='genericfuture') genericfuture 
OUTER APPLY (SELECT *
	FROM TS_PAYMENT_SECURITY_HISTORY s WITH (NOLOCK)
	WHERE t.SecurityId=s.SecurityId AND t.SecurityVersion=s.Version AND t.TradeType='payment') payment 
OUTER APPLY (SELECT *
	FROM TS_CROSS_CCY_SWAP_SECURITY_HISTORY s WITH (NOLOCK)
	WHERE t.SecurityId=s.SecurityId AND t.SecurityVersion=s.Version AND t.TradeType='crosscurrencyswap') crosscurrencyswap 
OUTER APPLY (SELECT *
	FROM TS_CAP_FLOOR_SECURITY_HISTORY s WITH (NOLOCK)
	WHERE t.SecurityId=s.SecurityId AND t.SecurityVersion=s.Version AND t.TradeType='capfloor') capfloor
OUTER APPLY (SELECT *
	FROM TS_FXFWD_SECURITY_HISTORY s WITH (NOLOCK)
	WHERE t.SecurityId=s.SecurityId AND t.SecurityVersion=s.Version AND t.TradeType='fxfwd') fxfwd 
OUTER APPLY (SELECT *
	FROM TS_TBA_SECURITY_HISTORY s WITH (NOLOCK)
	WHERE t.SecurityId=s.SecurityId AND t.SecurityVersion=s.Version AND t.TradeType='tba') tba 
OUTER APPLY (SELECT *
	FROM TS_CMO_SECURITY_HISTORY s WITH (NOLOCK)
	WHERE t.SecurityId=s.SecurityId AND t.SecurityVersion=s.Version AND t.TradeType='cmo') cmo 
OUTER APPLY (SELECT *
	FROM TS_MBS_SECURITY_HISTORY s WITH (NOLOCK)
	WHERE t.SecurityId=s.SecurityId AND t.SecurityVersion=s.Version AND t.TradeType='mbs') mbs 
OUTER APPLY (SELECT *
	FROM TS_CMSWAP_SPREAD_OPTION_SECURITY_HISTORY s WITH (NOLOCK)
	WHERE t.SecurityId=s.SecurityId AND t.SecurityVersion=s.Version AND t.TradeType='cmswapsprdopt') cmswapsprdopt