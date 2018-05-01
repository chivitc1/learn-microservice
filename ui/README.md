#RUN UI
- UI structure:
<code>
--ui<br>
----start.d<br>
------deploy.ini<br>
------http.ini<br>
----webapps<br>
------ui<br>
--------index.html<br>
--------multiplication-client.js<br>
--------gamification-client.js<br>
--------style.css
</code>

- Download Jetty
- Set $JETTY_HOME to jetty dir
- From ui working_dir module, run: java -jar $JETTY_HOME/start.jar

