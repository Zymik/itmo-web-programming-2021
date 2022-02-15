<#import "./common.ftlh" as c>

<@c.page>
    <#list posts?reverse as s>
        <@c.post s c.findBy(users, "id", s.userId) true/>
    </#list>
</@c.page>
