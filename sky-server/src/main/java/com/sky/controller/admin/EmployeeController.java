package com.sky.controller.admin;

import com.sky.constant.JwtClaimsConstant;
import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.properties.JwtProperties;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.EmployeeService;
import com.sky.utils.JwtUtil;
import com.sky.vo.EmployeeLoginVO;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * manage employee
 */
@RestController
@RequestMapping("/admin/employee")
@Slf4j
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private JwtProperties jwtProperties;

    /**
     * login
     *
     * @param employeeLoginDTO
     * @return
     */
    @PostMapping("/login")
    public Result<EmployeeLoginVO> login(@RequestBody EmployeeLoginDTO employeeLoginDTO) {
        log.info("start to log in：{}", employeeLoginDTO);

        Employee employee = employeeService.login(employeeLoginDTO);

        //after successfully logged in, create a jwt token
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.EMP_ID, employee.getId());
        String token = JwtUtil.createJWT(
                jwtProperties.getAdminSecretKey(),
                jwtProperties.getAdminTtl(),
                claims);

        EmployeeLoginVO employeeLoginVO = EmployeeLoginVO.builder()
                .id(employee.getId())
                .userName(employee.getUsername())
                .name(employee.getName())
                .token(token)
                .build();

        return Result.success(employeeLoginVO);
    }

    /**
     *logout
     *
     * @return
     */
    @PostMapping("/logout")
    @ApiOperation("employee log out")
    public Result<String> logout() {
        return Result.success();
    }

    /**
     *
     * @param employeeDTO
     * @return
     */
    @PostMapping
    @ApiOperation("add a new employee")
    public Result save(@RequestBody EmployeeDTO employeeDTO) {
        log.info("new :{}",employeeDTO);
        employeeService.save(employeeDTO);
        return Result.success();
    }

    /**
     * search employees by page
     * @param employeePageQueryDTO
     * @return
     */
    @GetMapping("/page")
    @ApiOperation("search employees by page")
    public Result<PageResult> page(EmployeePageQueryDTO employeePageQueryDTO) {
        log.info("search employee by page{}",employeePageQueryDTO);
        PageResult pageResult=employeeService.pageQuery(employeePageQueryDTO);
        return Result.success(pageResult);
    }
    @PostMapping("/status/{status}")
    public Result startOrStop(@PathVariable Integer status,Long id) {
        log.info("start or stop the account of an employee:{},{}",status,id);
        employeeService.startOrStop(status,id);
        return Result.success();
    }
    @GetMapping("/{id}")
    public Result<Employee> idSearch(@PathVariable Long id) {
        log.info("idSearch:{}",id);
        Employee employee = employeeService.idSearch(id);
        return Result.success(employee);
    }
    @PutMapping
    public Result udpate(@RequestBody EmployeeDTO employeeDTO) {
        log.info("udpate :{}",employeeDTO);
        employeeService.update(employeeDTO);
        return Result.success();
    }
}
