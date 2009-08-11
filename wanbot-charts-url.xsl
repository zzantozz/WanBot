<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

    <xsl:output method="text" />
    <xsl:strip-space elements="*"/>
    
    <xsl:template match="/thewan">http://chart.apis.google.com/chart?cht=p3&amp;chs=500x220&amp;chco=FF0000,FF8040,FFFF00,00FF00,00FFFF,0000FF,800080&amp;chd=t:<xsl:apply-templates select="/thewan/points" /></xsl:template>
    
    <xsl:template match="/thewan/points">
        <xsl:call-template name="names" />
        <xsl:text>&amp;chl=</xsl:text>
        <xsl:call-template name="points" />
    </xsl:template>
    
    <xsl:template name="names">
        <xsl:for-each select="peep">
            <xsl:sort select="@points" data-type="number" order="descending"/>
            <xsl:value-of select="@points" />
            <xsl:if test="not(position()=last())">,</xsl:if>
        </xsl:for-each>
    </xsl:template>
    
    <xsl:template name="points">
        <xsl:for-each select="peep">
            <xsl:sort select="@points" data-type="number" order="descending"/>
            <xsl:value-of select="@name" />
            <xsl:if test="not(position()=last())">|</xsl:if>
        </xsl:for-each>
    </xsl:template>
</xsl:stylesheet>