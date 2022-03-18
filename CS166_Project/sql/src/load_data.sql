DROP TABLE USR;
COPY USR (
	userId,
	passwor,
	email,
	name,
	dateOfBirth)
FROM 'USR.csv'
WITH DELIMITER ',';

DROP TABLE WORK_EXPR;
COPY WORK_EXPR(
	userId,
	company,
	role,
	location,
	startDate,
	endDate)
FROM 'WORK_EXPR.csv'
WITH DELIMITER ',';

DROP TABLE EDUCATIONAL_DETAILS;
COPY EDUCATIONAL_DETAILS (
	userId,
	instituitionName,
	major,
	degree,
	startdate,
	enddate)
FROM 'EDUCATIONAL_DETAILS.csv'
WITH DELIMITER ',';

DROP TABLE MESSAGE;
COPY MESSAGE (
	msgId,
	senderId,
	receiverId,
	contents,
	deleteStatus,
	status)
FROM 'MESSAGE.csv'
WITH DELIMITER ',';

DROP TABLE CONNECTION_USR;
COPY CONNECTION_USR (
	userId,
	connectionId,
	status)
FROM 'CONNECTION_USR.csv'
WITH DELIMITER ',';