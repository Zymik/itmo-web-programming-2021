<#-- @ftlvariable name="handle" type="java.lang.String" -->
<#-- @ftlvariable name="user_id" type="long" -->
<#-- @ftlvariable name="user_id" type="long" -->
<#import "./common.ftlh" as c>

<@c.page>
    <#assign n = users?size/>

    <#if user_id?has_content>
        <#assign n = c.findPositionBy(users, "id", user_id)/>
    <#elseif handle?has_content>
        <#assign n = c.findPositionBy(users, "handle", handle)/>
    </#if>

    <#if n?? && n < users?size>
        <#assign u = users[n]/>

        <div class="user_box">

            <div class=user_header> <@c.userlink u false/> </div>
            <img class=user_avatar alt="Avatar" title="Avatar"
                 src="/img/no-title.jpg">
            <ul class="user_attributes">
                <li>id: ${u.id}</li>
                <li>handle: ${u.handle}</li>
                <li>name: ${u.name}</li>
                <li>post count: <a href="/post?user_id=${u.id}">
                        ${c.countBy(posts, "userId", u.id)}
                    </a></li>
            </ul>
        </div>

        <div class="direction_arrows">
            <#if n != users?size - 1>
                <@lightedArrow users[n+1] "←"/>
            <#else>
                <@dimArrow "←"/>
            </#if>

            <#if n != 0>
                <@lightedArrow users[n-1] "→"/>
            <#else>
                <@dimArrow "→"/>
            </#if>

        </div>
    <#else>
        No such user
    </#if>
</@c.page>

<#macro lightedArrow user arrow>
    <a class="lighted_arrow" href="/user?handle=${user.handle}">${arrow}</a>
</#macro>

<#macro dimArrow arrow>
    <span class="dim_arrow">${arrow}</span>
</#macro>
