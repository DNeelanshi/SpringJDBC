package com.stackroute.dao;

import com.stackroute.model.Employee;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class EmployeeDao {

        JdbcTemplate template;

        public void setTemplate(JdbcTemplate template) {
            this.template = template;
        }
        public int save(Employee p){

            String sql = "insert into employeedtbl(name,salary,designation) values('"+p.getName()+"',"+p.getSalary()+",'"+p.getDesignation()+"')";

            return template.update(sql);

        }

        public int update(Employee emp){
            int id  = 2;
            String sql="update employeedtbl set name='"+emp.getName()+"', salary="+emp.getSalary()+",designation='"+emp.getDesignation()+"' where id="+emp.getId()+"";
            return template.update(sql);
        }
        public int delete(int id){
            String sql="delete from employeedtbl where id="+id+"";
            return template.update(sql);
        }
        public Employee getEmpById(int id){
            String sql="select * from employeedtbl where id=?";
            return template.queryForObject(sql,
                    new Object[]{id},new BeanPropertyRowMapper<Employee>(Employee.class));
        }
        public List<Employee> getEmployees(){
            return template.query("select * from employeedtbl",new RowMapper<Employee>(){
                public Employee mapRow(ResultSet rs, int row) throws SQLException {
                    Employee e=new Employee();
                    e.setId(rs.getInt(1));
                    e.setName(rs.getString(2));
                    e.setSalary(rs.getInt(3));
                    e.setDesignation(rs.getString(4));
                    return e;
                }
            });
        }
}

