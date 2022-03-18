
COPY USR (
	userId,
	password,
	email,
	name,
	dateOfBirth)
FROM 'USR.CSV'
WITH DELIMITER ',' CSV;

COPY WORK_EXPR(
	userId,
	company,
	role,
	location,
	startDate,
	endDate)
FROM 'Work_Ex.CSV'
WITH DELIMITER ',' CSV;

COPY EDUCATIONAL_DETAILS (
	userId,
	instituitionName,
	major,
	degree,
	startdate,
	enddate)
FROM 'Edu_Det.CSV'
WITH DELIMITER ',' CSV;

COPY MESSAGE (
	msgId,
	senderId,
	receiverId,
	contents,
	sendTime,
	deleteStatus,
	status)
FROM 'Message.CSV' 
WITH DELIMITER ',' CSV;

COPY CONNECTION_USR (
	userId,
	connectionId,
	status)
FROM 'Connection.CSV'
WITH DELIMITER ',' CSV;