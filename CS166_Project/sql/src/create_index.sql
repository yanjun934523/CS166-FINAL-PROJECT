CREATE INDEX index_USR_userId
ON USR(userId);

CREATE INDEX index_USR_name
ON USR(name);

CREATE INDEX index_USR_dateOfBirth
ON USR(dateOfBirth);

CREATE INDEX index_WORK_EXPR_userId
ON WORK_EXPR(userID);

CREATE INDEX index_EDUCATIONAL_DETAILS_userId
ON EDUCATIONAL_DETAILS(userId);

CREATE INDEX index_MESSAGE_msgId
ON MESSAGE(msgId);

CREATE INDEX index_MESSAGE_receiverId
ON MESSAGE(receiverId);

CREATE INDEX index_CONNECTION_USR_userId
ON CONNECTION_USR(userId);

CREATE INDEX index_CONNECTION_USR_connectionId
ON CONNECTION_USR(connectionId);

