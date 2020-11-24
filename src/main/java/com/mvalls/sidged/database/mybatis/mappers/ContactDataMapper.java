package com.mvalls.sidged.database.mybatis.mappers;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.mvalls.sidged.database.mybatis.dtos.ContactDataMyBatisDTO;

@Mapper
public interface ContactDataMapper {
	
	@Select("select * from contact_data where id = #{contactDataId}")
	ContactDataMyBatisDTO findContactDataById(Long contactDataId);

	@Insert("insert into contact_data (emails, phones) values "
			+ "(#{emails}, #{phones}) ")
	@Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
	Long insert(ContactDataMyBatisDTO contactData);

	@Update("update contact_data set emails = #{emails}, "
			+ "phones = #{phones} "
			+ "where id = #{id}")
	void update(ContactDataMyBatisDTO dto);

}
