package org.wanbot

import java.util.Properties


def xml = new File('datastore.xml').getText()
def stylesheet = new File('wanbot-basic.xsl').getText()
notifications.notify(notifications.stdout, notifications.xslConverter, xml, stylesheet)