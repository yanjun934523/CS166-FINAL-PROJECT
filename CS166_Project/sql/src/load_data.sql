
COPY USR (
	userId,
	password,
	email,
	name,
	dateOfBirth)
FROM 'USR.csv'
WITH DELIMITER ',';

COPY WORK_EXPR(
	userId,
	company,
	role,
	location,
	startDate,
	endDate)
FROM 'Work_Ex.csv'
WITH DELIMITER ',';

COPY EDUCATIONAL_DETAILS (
	userId,
	instituitionName,
	major,
	degree,
	startdate,
	enddate)
FROM 'Edu_Det.csv'
WITH DELIMITER ',';

COPY MESSAGE (
	msgId,
	senderId,
	receiverId,
	contents,
	deleteStatus,
	status)
FROM 'Message.csv'
WITH DELIMITER ',';

COPY CONNECTION_USR (
	userId,
	connectionId,
	status)
FROM 'Connection.csv'
WITH DELIMITER ',';