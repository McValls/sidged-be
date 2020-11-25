package com.mvalls.sidged.database.mybatis.mappers;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import com.mvalls.sidged.database.dtos.ClassFileDocumentDTO;

@Mapper
public interface ClassFileDocumentMapper {
	
	@Insert("insert into class_file_document "
			+ "(name, content, content_type, file_document_type, course_class_id) "
			+ "values "
			+ "(#{name}, #{content}, #{contentType}, #{fileDocumentType}, #{courseClassId})")
	@Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
	void insert(ClassFileDocumentDTO dto);

	@Select("select fd.* from class_file_document as fd "
			+ "inner join course_class cc on fd.course_class_id = cc.id "
			+ "inner join course c on cc.course_id = c.id "
			+ "where c.code = #{courseCode} and cc.class_number = #{classNumber}")
	List<ClassFileDocumentDTO> findByCourseCodeAndClassNumber(String courseCode, Integer classNumber);

	@Select("select * from class_file_document where id = #{id}")
	Optional<ClassFileDocumentDTO> findById(Long id);

	@Select("select fd.* from class_file_document as fd "
			+ "inner join course_class cc on fd.course_class_id = cc.id "
			+ "inner join course c on cc.course_id = c.id "
			+ "where c.code = #{courseCode}")
	List<ClassFileDocumentDTO> findByCourseCode(String courseCode);

}
