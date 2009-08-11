package org.wanbot

import java.util.Propertiesimport java.io.ByteArrayInputStream
def notifications = new Notifications()

def xml = new File('datastore.xml').getText()
def stylesheet = new File('wanbot-basic.xsl').getText()
notifications.notify(notifications.stdout, notifications.xslConverter, xml, stylesheet)
