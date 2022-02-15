<#import "commons.ftlh" as c>

<@c.page>
    <div class="users datatable">
        <div class="caption">User</div>
        <table>
            <thead>
            <tr>
                <th>Id</th>
                <th>Login</th>
                <th>Created</th>
            </tr>
            </thead>
            <tbody>
            <#if users?size==0>
                <tr class="noData">
                    <td colspan="3">
                        No data
                    </td>
                </tr>
            <#else>
                <#list users as tableUser>
                    <tr>
                        <td class="id">${tableUser.id}</td>
                        <td class="login">${tableUser.login}</td>
                        <td class="creationTime">${tableUser.creationTime}</td>
                        <td class="changeAbility">
                            <#assign type = getAbilityChanger(tableUser)>
                            <form method="post" action="">
                                <input type="hidden" value=${tableUser.id?string.computer} id="userId" name="userId"/>
                                <div class="button-field">
                                    <input type="submit" value=${type} id="type" name="type"/>
                                </div>
                            </form>
                        </td>
                    </tr>
                </#list>
            </#if>
            </tbody>
        </table>
        <#if idError??>
            <div class="error">${idError}</div>
        </#if>
        <#if typeError??>
            <div class="error">${typeError}</div>
        </#if>
        <!--<@c.error "changeAbility.userId"/>-->
        <!--<@c.error "changeAbility.type"/>-->
    </div>
</@c.page>

<#function getAbilityChanger tableUser>
    <#if tableUser.disable>
        <#return "enable">
    <#else>
        <#return "disable">
    </#if>
</#function>