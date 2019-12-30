package com.github.corese4rch.cvurl.easytest.utils;

import com.github.corese4rch.cvurl.easytest.asserts.rule.Rule;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.github.corese4rch.cvurl.easytest.asserts.rule.Rules.of;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private String name;


    public static Rule<User> hasName(String name) {
        return of(user -> user.getName().equals(name), "name should be " + name);
    }
}
