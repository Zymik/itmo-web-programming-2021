<#macro page>
    <!DOCTYPE html>
    <html lang="ru">
    <head>
        <meta charset="UTF-8">
        <title>Codeforces</title>
        <link rel="stylesheet" href="/css/normalize.css">
        <link rel="stylesheet" href="/css/style.css">
        <link rel="icon" href="/favicon.ico">
    </head>
    <body>
    <@header/>
    <div class="middle">
        <@sidebar/>
        <main>
            <#nested/>
        </main>
    </div>
    <@footer/>
    </body>
    </html>
</#macro>

<#macro header>
    <header>
        <a href="#" class="logo"><img alt="Codeforces" title="Codeforces"
                                      src="/img/codeforces-logo-with-telegram.png"></a>
        <div class="languages">
            <a href="#"><img alt="In English" title="In English"
                             src="/img/gb.png"></a>
            <a href="#"><img alt="In Russian" title="In Russian"
                             src="/img/ru.png"></a>
        </div>
        <div class="enter-or-register-box">
            <#if user??>
                <@userlink user true/>
                |
                <a href="/logout">Logout</a>
            <#else>
                <a href="/enter">Enter</a>
                |
                <a href="/register">Register</a>
            </#if>
        </div>
        <nav>
            <#assign items=[
            {"href": "/index", "name": "Home", "highlightedUri": ["/index", "/"]},
            {"href": "/users", "name": "Users", "highlightedUri": ["/users", "/user"]},
            {"href": "/misc/help", "name": "Help", "highlightedUri": ["/misc/help"]},
            {"href": "/contests", "name": "Contests", "highlightedUri": ["/contests"]}
            ]/>
            <ul>
                <#list items as item>
                    <li>
                        <#if item.highlightedUri?seq_contains(uri)>
                            <a class="highlighted_header" href="${item.href}">${item.name}</a>
                        <#else>
                            <a href="${item.href}">${item.name}</a>
                        </#if>
                    </li>
                </#list>
            </ul>
        </nav>
    </header>
</#macro>

<#macro sidebar>
    <aside>
        <#list posts?reverse as s>
            <section>
                <div class="header">Post #${s.id}</div>
                <div class="body">
                    <@postBody s.text true/>
                </div>
                <div class="footer">
                    <a href="/post?post_id=${s.id}">View all</a>
                </div>
            </section>

        </#list>
    </aside>
</#macro>

<#macro footer>
    <footer>
        2010-2021 &copy; by Mike Mirzayanov
    </footer>
</#macro>



<#macro userlink user nameOnly>
<#-- @ftlvariable name="u" type="ru.itmo.web.lesson4.model.User" -->
    <#if nameOnly>
        <a href="/user?handle=${user.handle}">${user.name}</a>
    <#else>
        <a class=${user.handleColor.name()} href="/user?handle=${user.handle}">${user.handle}</a>
    </#if>
</#macro>



<#macro post p user short>
    <article>
        <@subElement "title" p.title/>
        <div class="information">
            By <@userlink user false/>
        </div>
        <@postBody p.text short/>
        <@postFooter/>
    </article>
</#macro>

<#macro postBody body short>
    <#assign text = body/>
    <#if short && (body?length > 250)>
        <#assign text = body?substring(0, 251) + ".."/>
    </#if>
    <@subElement "body" text/>
</#macro>

<#macro subElement className data>
    <div class=${className}>
        ${data}
    </div>
</#macro>

<#macro postFooter>
    <div class="footer">
        <div class="left">
            <img src="img/voteup.png" title="Vote Up" alt="Vote Up"/>
            <span class="positive-score">+1</span>
            <img src="img/votedown.png" title="Vote Down" alt="Vote Down"/>
        </div>
        <div class="right">
            <img src="img/date_16x16.png" title="Publish Time" alt="Publish Time"/>
            2 days ago
            <img src="img/comments_16x16.png" title="Comments" alt="Comments"/>
            <a href="#">68</a>
        </div>
    </div>
</#macro>


<#function findBy items key value>
    <#list items as item>
        <#if item[key] == value>
            <#return item/>
        </#if>
    </#list>
</#function>

<#function countBy items key value>
    <#assign count = 0/>
    <#list items as item>
        <#if item[key] == value>
            <#assign count++/>
        </#if>
    </#list>
    <#return count/>
</#function>

<#function findPositionBy items key value>
    <#assign x = 0/>
    <#list items as item>
        <#if item[key] == value>
            <#break/>
        </#if>
        <#assign x++/>
    </#list>
    <#return x/>
</#function>
