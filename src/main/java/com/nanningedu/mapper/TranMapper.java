package com.nanningedu.mapper;

import com.nanningedu.domain.TranVO;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface TranMapper {

    @Select("select d.value name,count(*) value from tbl_tran t inner join tbl_dic_value d on t.stage = d.id group by d.value")
    List<TranVO> selectByStage();

}
