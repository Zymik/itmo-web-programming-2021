<#-- @ftlvariable name="post_id" type="long" -->
<#-- @ftlvariable name="user_id" type="long" -->
<#-- @ftlvariable name="p" type="ru.itmo.web.lesson4.model.Post" -->
<#import "./common.ftlh" as c>
<@c.page>
    <#if post_id??>
        <#assign p = c.findBy(posts, "id", post_id)/>
        <@c.post p c.findBy(users, "id", p.userId), false/>
    <#elseif user_id??>
        <#assign user = c.findBy(users, "id", user_id)>
        <#list posts?reverse as p>
            <#if user.id == p.userId>
                <@c.post p user true/>
            </#if>
        </#list>
    <#else>
        No one post matches parameters
    </#if>
</@c.page>