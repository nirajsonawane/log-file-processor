package com.logprocessor.service;

import com.logprocessor.model.BadRecordReport;

public interface LogMessageProcessorDaoService {

	void insertBadRecord(BadRecordReport badRecord);

}
