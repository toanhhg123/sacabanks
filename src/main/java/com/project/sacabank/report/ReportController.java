package com.project.sacabank.report;

import static com.project.sacabank.utils.Constants.REPORT_API;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.sacabank.base.BaseController;

@RestController
@RequestMapping(path = REPORT_API)
public class ReportController extends BaseController {

  @Autowired
  ReportService service;

  @GetMapping("")
  public ResponseEntity<?> getReport() {
    var user = this.getUserInfo();
    return this.onSuccess(service.getReport(user));
  }

}
