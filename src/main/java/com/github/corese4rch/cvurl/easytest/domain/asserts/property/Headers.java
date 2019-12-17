package com.github.corese4rch.cvurl.easytest.domain.asserts.property;

import com.github.corese4rch.cvurl.easytest.domain.EasyRequest;

import java.util.Map;
import java.util.function.Predicate;

public class Headers extends RequestProperty<Map<String, String>> {
  
  @Override
  protected String getName() {
    return "headers";
  }

  @Override
  protected Predicate<EasyRequest> getPredicate(Predicate<Map<String, String>> propertyPredicate) {
    return request -> propertyPredicate.test(request.getHeaders());
  }
}
