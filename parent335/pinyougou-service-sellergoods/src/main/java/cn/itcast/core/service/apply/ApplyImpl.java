package cn.itcast.core.service.apply;

import cn.itcast.core.dao.specification.SpecificationDao;
import cn.itcast.core.dao.specification.SpecificationOptionDao;
import cn.itcast.core.pojo.specification.Specification;
import cn.itcast.core.pojo.specification.SpecificationOption;
import cn.itcast.core.vo.SpecVo;
import com.alibaba.dubbo.config.annotation.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ApplyImpl implements Apply {

    @Resource
    private SpecificationDao specificationDao;
    @Resource
    private SpecificationOptionDao specificationOptionDao;

    /**
     * 规格申请
     * @param specVo
     */
    @Override
    public void apply(SpecVo specVo) {

        // 保存规格
        Specification specification = specVo.getSpecification();
        specification.setStatus("0");
        // 返回自增主键的id，需要在映射文件中去配置
        specificationDao.insertSelective(specification);
        // 保存规格选项
        List<SpecificationOption> specificationOptionList = specVo.getSpecificationOptionList();
        if(specificationOptionList != null && specificationOptionList.size() > 0){
            // 设置外键
            for (SpecificationOption specificationOption : specificationOptionList) {
                specificationOption.setSpecId(specification.getId());
                // 保存：一个个的保存
//                specificationOptionDao.insertSelective(specificationOption);
            }
            // 批量保存
            specificationOptionDao.insertSelectives(specificationOptionList);
        }
    }
}
