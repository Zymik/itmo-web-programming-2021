
<#-- @ftlvariable name="error" type="java.lang.String" -->
<#import "commons.ftlh" as c>

<@c.page>

    <div class="text-box" xmlns="http://www.w3.org/1999/html">
        <div class="header">Make Notice</div>
        <div class="body">
            <form method="post">
                <input type="hidden" name="action" value="noticeText"/>
                <div class="field">
                    <div class="name">
                        <label for="noticeText">Text</label>
                    </div>
                    <textarea id="noticeText" name="noticeText" type="text">${noticeText.noticeText!}</textarea>

                </div>
                <@c.error "noticeText.noticeText"/>
                <div class="button-field">
                    <input type="submit" value="Enter">
                </div>
            </form>
        </div>
    </div>
</@c.page>
