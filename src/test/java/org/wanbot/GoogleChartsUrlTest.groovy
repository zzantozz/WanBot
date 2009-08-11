package org.wanbot

import static org.junit.Assert.*
import static org.hamcrest.Matchers.*
import org.junit.Test
import javax.xml.transform.TransformerFactoryimport javax.xml.transform.stream.StreamSourceimport javax.xml.transform.stream.StreamResultimport java.io.Filepublic class GoogleChartsUrlTest {

    def notifications = new Notifications()
    def expectedUrl = 'http://chart.apis.google.com/chart?cht=p3&chs=500x220&chco=FF0000,FF8040,FFFF00,00FF00,00FFFF,0000FF,800080&chd=t:50,30,20&chl=foo|bar|baz'
    def pointsList = [
        ['foo', 50],
        ['bar', 30],
        ['baz', 20]
    ]
    def xml = '''\
        <thewan>
          <peeps>
            <peep name="foo" />
            <peep name="bar" />
            <peep name="baz" />
          </peeps>
          <points>
            <peep name="foo" points="50" />
            <peep name="bar" points="30" />
            <peep name="baz" points="20" />
          </points>
        </thewan>
    '''
    
    @Test
    void test_google_charts_url_from_list() {
        def actualUrl = notifications.googleChartsUrlFromPointsList(pointsList)
        assertThat("Google Charts URL", actualUrl, equalTo(expectedUrl))
    }
    
    @Test
    void test_google_charts_url_from_xsl() {
        def factory = TransformerFactory.newInstance()
        def transformer = factory.newTransformer(new StreamSource(new File('wanbot-charts-url.xsl')))
        def writer = new StringWriter()
        transformer.transform(new StreamSource(new StringReader(xml)), new StreamResult(writer))
        assertThat("Google Charts URL", writer.toString(), equalTo(expectedUrl))
    }
    
    @Test
    void test_google_charts_url_created_by_notifications() {
        def url = notifications.googleChartsUrlFromDatastore(xml)
        assertThat("Google Charts URL", url, equalTo(expectedUrl))
    }
}
