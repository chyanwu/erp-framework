package com.chenyanwu.erp.erpframework.exception;

import com.chenyanwu.erp.erpframework.common.util.SpringContextHolder;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.Locale;

/**
 * Created by wd on 2016-7-20.
 */
public class MsgContentStruct {
  private String key;
  private String value ;

  private static MessageSource messageSource= SpringContextHolder.getBean(MessageSource.class);

  public MsgContentStruct(String key, String value){
    this.key = key;
    this.value = value;
  }

  public String Key() {
    return key;
  }

  public String Value() {
    //默认采用的是acceptheaderLocaleResolver，检查http头的accept_language
    Locale locale1= LocaleContextHolder.getLocale();
    if(locale1!=null){//如果指定了国际化
      this.value=messageSource.getMessage(key,null,locale1);
    }
    return  this.value;
  }

  public String FormatValue(Object... paras){
    //默认采用的是acceptheaderLocaleResolver，检查http头的accept_language
    Locale locale1= LocaleContextHolder.getLocale();
    if(locale1!=null){//如果指定了国际化
      this.value=messageSource.getMessage(key,null,locale1);
    }

    return  String.format(value, paras);
  }

}
