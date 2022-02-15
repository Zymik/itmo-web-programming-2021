<#-- @ftlvariable name="talks" type="ru.itmo.wp.web.page.TalksPage.talkRecord[]" -->
<#-- @ftlvariable name="users" type="ru.itmo.wp.model.domain.User[]" -->
<#-- @ftlvariable name="error" type="java.lang.String" -->
<#import "commons.ftlh" as c>

<@c.page>

    <div class="text-box" xmlns="http://www.w3.org/1999/html">
        <div class="header">Send Message</div>
        <div class="body">
            <form method="post" action="">
                <input type="hidden" name="action" value="send"/>
                <div class="field">
                    <div class="name">
                        <label for="targetUser">Target User</label>
                    </div>
                    <div class="value">
                        <select id="targetUser" name="targetUser" type="text">
                            <#list users as user>
                                <option>${user.login} </option>
                            </#list>
                        </select>
                    </div>
                </div>
                <div class="field">
                    <div class="name">
                        <label for="text">Text</label>
                    </div>
                    <textarea id="text" name="text" type="text"></textarea>
                </div>
                <#if error??>
                    <div class="error">
                        ${error!}
                    </div>
                </#if>
                <div class="button-field">
                    <input type="submit" value="Enter">
                </div>
            </form>
        </div>
    </div>
    <#if talks?hasContent>
    <div class="datatable">
        <div class="caption">Talk</div>
        <table>
            <thead>
            <tr>
                <th>Id</th>
                <th>Source User</th>
                <th>Target User</th>
                <th>Text</th>
                <th>Time</th>
            </tr>
            </thead>
            <tbody>
            <#list talks as talk>
                <tr>
                    <td>${talk.id()}</td>
                    <td>${talk.sourceUser()}</td>
                    <td>${talk.targetUser()}</td>
                    <td>${talk.text()}</td>
                    <td>${talk.creationTime()}</td>
                </tr>
            </#list>
            </tbody>
        </table>
    </div>
    </#if>
</@c.page>
