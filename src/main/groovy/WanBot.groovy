import groovy.util.ProxyGenerator
import javax.mail.search.SearchTerm;
import javax.mail.event.*;

def updateData(peepName, addPoints) {
    println '  updating with ' + peepName + ', ' + addPoints
    def datafile = new File("datastore.xml")
    def records = new XmlParser().parse(datafile)
    def peep = records.peeps.peep.find{ it.@name.equals(peepName) || it.alias.text().equals(peepName) }
    if (peep == null) {
        peep = records.peeps[0].appendNode('peep', [name: peepName])
        pointsNode = records.points[0].appendNode('peep', [name: peepName, points: 0])
    } else {
        pointsNode = records.points.peep.find{ it.@name.equals(peep.@name) }
    pointsNode.@points = pointsNode.@points.toInteger() + addPoints
    new XmlNodePrinter(new PrintWriter(new FileWriter(datafile))).print(records)
}

class WaNSearch extends SearchTerm {
    public boolean match(Message message) {
//        def condition = to.toLowerCase().contains("thewanbot@gmail.com")
        return condition
    }
}

def doWithMessages(cmd) {
    Session session = Session.getDefaultInstance(System.getProperties())
    Store store = session.getStore("pop3s")
    store.connect('pop.gmail.com', 995, credentials().user, credentials().password)
    Folder folder = store.getDefaultFolder().getFolder("INBOX")
    folder.open(Folder.READ_ONLY)
    folder.search(new WaNSearch()).each(cmd)
    folder.close(false)
    store.close()
}

def parseMessage(text) {
    pointMatches.each{
        _, name, points ->
        if (points.startsWith("+")) points = points.substring(1)
        points = points.toInteger()
    }
}

    def content = msg.getContent()
    if (content instanceof Multipart) {
        for (i in 0..content.getCount() - 1) {
            def part = content.getBodyPart(i)
            if (part.contentType.contains('text/plain')) {
                parseMessage(part.content)
                break
            }
        }
    } else {
        parseMessage(content)
    }
}