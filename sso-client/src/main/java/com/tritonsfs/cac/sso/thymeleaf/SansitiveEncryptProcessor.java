package com.tritonsfs.cac.sso.thymeleaf;

import com.tritonsfs.cac.sso.remote.model.CacRedisMenu;
import com.tritonsfs.cac.util.common.string.StringUtils;
import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.context.WebEngineContext;
import org.thymeleaf.engine.AttributeName;
import org.thymeleaf.model.IModel;
import org.thymeleaf.model.IModelFactory;
import org.thymeleaf.model.IProcessableElementTag;
import org.thymeleaf.processor.element.AbstractAttributeTagProcessor;
import org.thymeleaf.processor.element.IElementTagStructureHandler;
import org.thymeleaf.templatemode.TemplateMode;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.regex.Pattern;


/**
 * @Time 2018/5/7
 * @Author zlian
 */
public class SansitiveEncryptProcessor extends AbstractAttributeTagProcessor {
    private static final String ATTRIBUTE_NAME = "check";
    private static final int PRECEDENCE = 300;


    protected SansitiveEncryptProcessor( String dialectPrefix) {
        super(
                TemplateMode.HTML, // 处理thymeleaf 的模型
                dialectPrefix, // 标签前缀名
                null, // No tag name: match any tag name
                false, // No prefix to be applied to tag name
                ATTRIBUTE_NAME, // 标签前缀的 属性 例如：< risk:sansiEncrypt="">
                true, // Apply dialect prefix to attribute name
                PRECEDENCE, // Precedence (inside dialect's precedence)
                true); // Remove the matched attribute afterwards
    }


    @Override
    protected void doProcess(ITemplateContext context,
                             IProcessableElementTag tag, AttributeName attributeName,
                             String attributeValue, IElementTagStructureHandler structureHandler) {
        final String url = tag.getAttributeValue(attributeName); //获取标签内容表达式
        //获取session内的menuList
        WebEngineContext webEngineContext = (WebEngineContext)context;
        HttpSession session = webEngineContext.getSession();
        List<CacRedisMenu> menuList = (List<CacRedisMenu>)session.getAttribute("menus");
        //是否隐藏 标识
        boolean flag = true;
        if(menuList!=null){
            for(CacRedisMenu crm : menuList){
                if(Pattern.matches(crm.getUrl(),url)){
                    flag=false;
                    break;
                }
            }
        }
        if(flag){
            //创建模型
            final IModelFactory modelFactory = context.getModelFactory();
            //标签名
            final String elementCompleteName = tag.getElementCompleteName();
            modelFactory.setAttribute(tag,"hidden","hidden");
            final IModel model = modelFactory.createModel(tag);
            //添加模型 标签
            model.add(modelFactory.createCloseElementTag(elementCompleteName));
            //替换页面标签
            structureHandler.replaceWith(model, false);
        }
    }

}
