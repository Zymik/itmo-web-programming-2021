<#-- @ftlvariable name="handle" type="java.lang.String" -->
<#-- @ftlvariable name="user_id" type="long" -->
<#-- @ftlvariable name="user_id" type="long" -->
<#import "commons.ftlh" as c>
<#import "/spring.ftl" as spring>

<@c.page>
    <#if pageUser??>
        <div class="user_box">
            <div class=user_header> ${pageUser.login} </div>
            <img class=user_avatar alt="Avatar" title="Avatar"
                 src=<@spring.url "/img/no-title.jpg"/>>

            <ul class="user_attributes">
                <li>id: ${pageUser.id}</li>
                <li>creation time: ${pageUser.creationTime}</li>
            </ul>
        </div>

    <#else>
        No such user
    </#if>
</@c.page>


