<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

    <xsl:output method="text" />
    <xsl:strip-space elements="*"/>

    <xsl:template match="/thewan/points">
        <xsl:for-each select="peep">
            <xsl:sort select="@points" data-type="number" order="descending"/>
            <xsl:value-of select="@name" /> : <xsl:value-of select="@points" />
            <xsl:text>
</xsl:text>
        </xsl:for-each>
    </xsl:template>

    <xsl:template match="/thewan/peeps" />
</xsl:stylesheet>