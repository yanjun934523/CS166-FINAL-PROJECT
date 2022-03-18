CREATE INDEX index_USR_userId
ON USR(userId);

CREATE INDEX index_USR_password
ON USR(password);

CREATE INDEX index_USR_email
ON USR(email);

CREATE INDEX index_USR_name
ON USR(name);

CREATE INDEX index_USR_dateOfBirth
ON USR(dateOfBirth);

CREATE INDEX index_WORK_EXPR_userId
ON WORK_EXPR(userID);

CREATE INDEX index_WORK_EXPR_company
ON WORK_EXPR(company);

CREATE INDEX index_WORK_EXPR_role
ON WORK_EXPR(role);

CREATE INDEX index_WORK_EXPR_location
ON WORK_EXPR(location);

CREATE INDEX index_WORK_EXPR_startDate
ON WORK_EXPR(startDate);

CREATE INDEX index_WORK_EXPR_endDate
ON WORK_EXPR(endDate);

CREATE INDEX index_EDUCATIONAL_DETAILS_userId
ON EDUCATIONAL_DETAILS(userId);

CREATE INDEX index_EDUCATIONAL_DETAILS_instituitionName
ON EDUCATIONAL_DETAILS(instituitionName);

CREATE INDEX index_EDUCATIONAL_DETAILS_major
ON EDUCATIONAL_DETAILS(major);

CREATE INDEX index_EDUCATIONAL_DETAILS_degree
ON EDUCATIONAL_DETAILS(degree);

CREATE INDEX index_EDUCATIONAL_DETAILS_startdate
ON EDUCATIONAL_DETAILS(startdate);

CREATE INDEX index_EDUCATIONAL_DETAILS_enddate
ON EDUCATIONAL_DETAILS(enddate);

CREATE INDEX index_MESSAGE_msgId
ON MESSAGE(msgId);

CREATE INDEX index_MESSAGE_receiverId
ON MESSAGE(receiverId);

CREATE INDEX index_MESSAGE_sendTime
ON MESSAGE(sendTime);

CREATE INDEX index_MESSAGE_deleteStatus
ON MESSAGE(deleteStatus);

CREATE INDEX index_MESSAGE_status
ON MESSAGE(status);

CREATE INDEX index_CONNECTION_USR_userId
ON CONNECTION_USR(userId);

CREATE INDEX index_CONNECTION_USR_connectionId
ON CONNECTION_USR(connectionId);

CREATE INDEX index_CONNECTION_USR_status
ON CONNECTION_USR(status);

