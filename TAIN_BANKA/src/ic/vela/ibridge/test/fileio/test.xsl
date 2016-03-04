<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xml:output method="xml" />
	
	<xsl:template match="/">
		<Org01Record>
			<xsl:apply-templates select="StandardRecord" />
		</Org01Record>
	</xsl:template>
	
	<xsl:template match="StandardRecord">
		<Field01><xsl:value-of select="Field01" /></Field01>
		<Field02><xsl:value-of select="Field02" /></Field02>
		<Field03><xsl:value-of select="Field06" /></Field03>
		<Field04><xsl:value-of select="Field07" /></Field04>
		<Field05><xsl:value-of select="Field08" /></Field05>
		<xsl:for-each select="Field11">
			<Field06>
				<SubField0601><xsl:value-of select="SubField1101" /></SubField0601>
				<SubField0602><xsl:value-of select="SubField1102" /></SubField0602>
				<SubField0603><xsl:value-of select="SubField1103" /></SubField0603>
			</Field06>
		</xsl:for-each>
		<xsl:for-each select="Field12">
			<Field07><xsl:value-of select="name(.)"/> : <xsl:value-of select="."/></Field07>
		</xsl:for-each>
	</xsl:template>
	
</xsl:stylesheet>
