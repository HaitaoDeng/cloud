package com.meiying.dao;

import com.meiying.common.entity.SysLog;
import org.springframework.data.jpa.repository.JpaRepository;


public interface SysLogDAO extends JpaRepository<SysLog, Long> {
}
