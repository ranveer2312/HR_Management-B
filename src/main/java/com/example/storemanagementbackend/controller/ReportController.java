package com.example.storemanagementbackend.controller;

import com.example.storemanagementbackend.model.Report;
import com.example.storemanagementbackend.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;
import org.springframework.core.io.FileSystemResource;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.util.UUID;
import java.util.ArrayList;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import com.example.storemanagementbackend.dto.ReportWithEmployeeDTO;
import com.example.storemanagementbackend.model.Employee;
import com.example.storemanagementbackend.repository.EmployeeRepository;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/reports") // Base URL for all report-related endpoints
@CrossOrigin(origins = "https://hr-management-f.vercel.app") // Allow requests from your Next.js frontend
public class ReportController {

    @Autowired
    private ReportService reportService;

    @Autowired
    private EmployeeRepository employeeRepository;

    // GET all reports or filter by type/subtype
    @GetMapping
    public ResponseEntity<List<ReportWithEmployeeDTO>> getAllReports(
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String subtype) {

        List<Report> reports;
        if (type != null && !type.equalsIgnoreCase("all")) {
            if (type.equalsIgnoreCase("employee") && subtype != null && !subtype.equalsIgnoreCase("all")) {
                reports = reportService.getReportsByTypeAndSubtype(type, subtype);
            } else {
                reports = reportService.getReportsByType(type);
            }
        } else {
            reports = reportService.getAllReports();
        }
        List<ReportWithEmployeeDTO> result = reports.stream().map(report -> {
            ReportWithEmployeeDTO dto = new ReportWithEmployeeDTO();
            dto.setId(report.getId());
            dto.setType(report.getType());
            dto.setSubtype(report.getSubtype());
            dto.setTitle(report.getTitle());
            dto.setContent(report.getContent());
            dto.setDate(report.getDate());
            dto.setSubmittedBy(report.getSubmittedBy());
            // Map customer report fields
            dto.setCustomerName(report.getCustomerName());
            dto.setDesignation(report.getDesignation());
            dto.setLandlineOrMobile(report.getLandlineOrMobile());
            dto.setEmailId(report.getEmailId());
            dto.setRemarks(report.getRemarks());
            dto.setProductOrRequirements(report.getProductOrRequirements());
            dto.setCompany(report.getCompany());
            // Always set the division from the report (for customer reports, this is the
            // dropdown value)
            dto.setDivision(report.getDivision());
            // Map new OEM/order/competitor fields
            dto.setSlNo(report.getSlNo());
            dto.setItemDescription(report.getItemDescription());
            dto.setCompetitor(report.getCompetitor());
            dto.setModelNumber(report.getModelNumber());
            dto.setUnitPrice(report.getUnitPrice());
            dto.setPoNumber(report.getPoNumber());
            dto.setOrderDate(report.getOrderDate());
            dto.setItem(report.getItem());
            dto.setPartNumber(report.getPartNumber());
            dto.setXmwPrice(report.getXmwPrice());
            dto.setUnitTotalOrderValue(report.getUnitTotalOrderValue());
            dto.setTotalPoValue(report.getTotalPoValue());
            dto.setXmwInvoiceRef(report.getXmwInvoiceRef());
            dto.setXmwInvoiceDate(report.getXmwInvoiceDate());
            dto.setNre(report.getNre());
            dto.setQuoteDate(report.getQuoteDate());
            dto.setQuantity(report.getQuantity());
            // Map new standard OEM fields
            dto.setQuotationNumber(report.getQuotationNumber());
            dto.setProductDescription(report.getProductDescription());
            dto.setXmwValue(report.getXmwValue());
            // Map employee details if type is employee or customer
            if (report.getSubmittedBy() != null) {
                Employee emp = employeeRepository.findByEmployeeId(report.getSubmittedBy()).orElse(null);
                if (emp != null) {
                    dto.setEmployeeName(emp.getEmployeeName());
                    dto.setEmployeeId(emp.getEmployeeId());
                    // Only set division from employee for employee reports
                    if ("employee".equalsIgnoreCase(report.getType())) {
                        dto.setDivision(emp.getDepartment());
                    }
                } else {
                    dto.setEmployeeId(report.getSubmittedBy());
                }
            }
            return dto;
        }).collect(Collectors.toList());
        return ResponseEntity.ok(result);
    }

    // GET a report by ID
    @GetMapping("/{id}")
    public ResponseEntity<Report> getReportById(@PathVariable Long id) {
        Optional<Report> report = reportService.getReportById(id);
        return report.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // GET reports by employee ID
    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<ReportWithEmployeeDTO>> getReportsByEmployeeId(@PathVariable String employeeId) {
        List<Report> reports = reportService.getReportsByEmployeeId(employeeId);
        List<ReportWithEmployeeDTO> result = reports.stream().map(report -> {
            ReportWithEmployeeDTO dto = new ReportWithEmployeeDTO();
            dto.setId(report.getId());
            dto.setType(report.getType());
            dto.setSubtype(report.getSubtype());
            dto.setTitle(report.getTitle());
            dto.setContent(report.getContent());
            dto.setDate(report.getDate());
            dto.setSubmittedBy(report.getSubmittedBy());
            // Map customer report fields
            dto.setCustomerName(report.getCustomerName());
            dto.setDesignation(report.getDesignation());
            dto.setLandlineOrMobile(report.getLandlineOrMobile());
            dto.setEmailId(report.getEmailId());
            dto.setRemarks(report.getRemarks());
            dto.setProductOrRequirements(report.getProductOrRequirements());
            dto.setCompany(report.getCompany());
            // Always set the division from the report (for customer reports, this is the
            // dropdown value)
            dto.setDivision(report.getDivision());
            // Map new OEM/order/competitor fields
            dto.setSlNo(report.getSlNo());
            dto.setItemDescription(report.getItemDescription());
            dto.setCompetitor(report.getCompetitor());
            dto.setModelNumber(report.getModelNumber());
            dto.setUnitPrice(report.getUnitPrice());
            dto.setPoNumber(report.getPoNumber());
            dto.setOrderDate(report.getOrderDate());
            dto.setItem(report.getItem());
            dto.setPartNumber(report.getPartNumber());
            dto.setXmwPrice(report.getXmwPrice());
            dto.setUnitTotalOrderValue(report.getUnitTotalOrderValue());
            dto.setTotalPoValue(report.getTotalPoValue());
            dto.setXmwInvoiceRef(report.getXmwInvoiceRef());
            dto.setXmwInvoiceDate(report.getXmwInvoiceDate());
            dto.setNre(report.getNre());
            dto.setQuoteDate(report.getQuoteDate());
            dto.setQuantity(report.getQuantity());
            // Map new standard OEM fields
            dto.setQuotationNumber(report.getQuotationNumber());
            dto.setProductDescription(report.getProductDescription());
            dto.setXmwValue(report.getXmwValue());
            // Map employee details if type is employee or customer
            if (report.getSubmittedBy() != null) {
                Employee emp = employeeRepository.findByEmployeeId(report.getSubmittedBy()).orElse(null);
                if (emp != null) {
                    dto.setEmployeeName(emp.getEmployeeName());
                    dto.setEmployeeId(emp.getEmployeeId());
                    // Only set division from employee for employee reports
                    if ("employee".equalsIgnoreCase(report.getType())) {
                        dto.setDivision(emp.getDepartment());
                    }
                } else {
                    dto.setEmployeeId(report.getSubmittedBy());
                }
            }
            return dto;
        }).collect(Collectors.toList());
        return ResponseEntity.ok(result);
    }

    // GET all unique divisions for customer reports
    @GetMapping("/customer-divisions")
    public ResponseEntity<List<String>> getCustomerDivisions() {
        List<String> divisions = reportService.getReportsByType("customer").stream()
                .map(Report::getDivision)
                .filter(div -> div != null && !div.trim().isEmpty())
                .distinct()
                .collect(Collectors.toList());
        return ResponseEntity.ok(divisions);
    }

    // GET all unique companies for customer reports
    @GetMapping("/customer-companies")
    public ResponseEntity<List<String>> getCustomerCompanies() {
        List<String> companies = reportService.getReportsByType("customer").stream()
                .map(Report::getCompany)
                .filter(company -> company != null && !company.trim().isEmpty())
                .distinct()
                .collect(Collectors.toList());
        return ResponseEntity.ok(companies);
    }

    // CREATE a new report (JSON endpoint)
    @PostMapping
    public ResponseEntity<Report> createReport(@RequestBody Report report) {
        if (report.getDate() == null) {
            report.setDate(LocalDate.now());
        }
        if (report.getStatus() == null || report.getStatus().isEmpty()) {
            report.setStatus("submitted");
        }
        Report createdReport = reportService.createReport(report);
        return new ResponseEntity<>(createdReport, HttpStatus.CREATED);
    }

    // CREATE a new report with file upload
    @PostMapping(value = "/with-files", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Report> createReportWithFiles(
            @RequestParam("type") String type,
            @RequestParam("title") String title,
            @RequestParam("content") String content,
            @RequestParam("submittedBy") String submittedBy,
            @RequestParam(value = "attachments", required = false) List<String> attachments) {

        Report report = new Report();
        report.setType(type);
        report.setTitle(title);
        report.setContent(content);
        report.setSubmittedBy(submittedBy);
        report.setDate(LocalDate.now());
        report.setStatus("submitted");
        report.setAttachments(attachments);

        Report createdReport = reportService.createReport(report);
        return new ResponseEntity<>(createdReport, HttpStatus.CREATED);
    }

    // UPDATE an existing report
    @PutMapping("/{id}")
    public ResponseEntity<Report> updateReport(@PathVariable Long id, @RequestBody Report reportDetails) {
        try {
            Report updatedReport = reportService.updateReport(id, reportDetails);
            return ResponseEntity.ok(updatedReport);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE a report
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReport(@PathVariable Long id) {
        try {
            reportService.deleteReport(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            // Handle case where report might not exist for deletion
            return ResponseEntity.notFound().build();
        }
    }

    // VIEW uploaded document
    @GetMapping("/{id}/view")
    public ResponseEntity<Resource> viewReport(@PathVariable Long id) {
        try {
            Optional<Report> reportOpt = reportService.getReportById(id);
            if (!reportOpt.isPresent() || reportOpt.get().getAttachments() == null
                    || reportOpt.get().getAttachments().isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            String fileName = reportOpt.get().getAttachments().get(0);
            Path filePath = Paths.get("uploads", fileName);
            File file = filePath.toFile();

            if (!file.exists()) {
                return ResponseEntity.notFound().build();
            }

            Resource resource = new FileSystemResource(file);
            String contentType = getContentType(fileName);

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + fileName + "\"")
                    .body(resource);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // DOWNLOAD uploaded document
    @GetMapping("/{id}/download")
    public ResponseEntity<Resource> downloadReport(@PathVariable Long id) {
        try {
            Optional<Report> reportOpt = reportService.getReportById(id);
            if (!reportOpt.isPresent() || reportOpt.get().getAttachments() == null
                    || reportOpt.get().getAttachments().isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            String fileName = reportOpt.get().getAttachments().get(0);
            Path filePath = Paths.get("uploads", fileName);
            File file = filePath.toFile();

            if (!file.exists()) {
                return ResponseEntity.notFound().build();
            }

            Resource resource = new FileSystemResource(file);
            String contentType = getContentType(fileName);

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                    .body(resource);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Simple file upload endpoint
    @PostMapping("/upload")
    public ResponseEntity<String> uploadFiles(@RequestParam("files") MultipartFile[] files) {
        try {
            String uploadDir = "uploads";
            File uploadDirFile = new File(uploadDir);
            if (!uploadDirFile.exists()) {
                uploadDirFile.mkdirs();
            }

            List<String> uploadedFiles = new ArrayList<>();
            for (MultipartFile file : files) {
                if (!file.isEmpty()) {
                    String originalFilename = file.getOriginalFilename();
                    String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
                    String uniqueFilename = UUID.randomUUID().toString() + fileExtension;

                    Path filePath = Paths.get(uploadDir, uniqueFilename);
                    Files.copy(file.getInputStream(), filePath);
                    uploadedFiles.add(uniqueFilename);
                }
            }

            return ResponseEntity.ok("Files uploaded successfully: " + uploadedFiles);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Upload failed: " + e.getMessage());
        }
    }

    private String getContentType(String fileName) {
        String extension = fileName.substring(fileName.lastIndexOf('.') + 1).toLowerCase();
        switch (extension) {
            case "pdf":
                return "application/pdf";
            case "doc":
                return "application/msword";
            case "docx":
                return "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
            case "xls":
                return "application/vnd.ms-excel";
            case "xlsx":
                return "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
            case "jpg":
            case "jpeg":
                return "image/jpeg";
            case "png":
                return "image/png";
            default:
                return "application/octet-stream";
        }
    }
}
