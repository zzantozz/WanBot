package org.wanbot

import java.util.Properties

def messageHandler = {
    def messageAssignments = bot.dissectMailMessage(it, { bot.findPointAssignmentsInMessageText(it) })
    bot.handleMessageAssignments(messageAssignments[0], messageAssignments[1],
        { assigner, assignee, points -> bot.doAssignment(assigner, assignee, points) })
}

bot.doWithMessages{ bot.withWanFilter(it, messageHandler) }