<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<xml:output method="xml" />
	
	<xsl:template match="/">
		<StandardRecord>
			<xsl:apply-templates select="Org01Record" />
		</StandardRecord>
	</xsl:template>
	
	<xsl:template match="Org01Record">
		<Field01><xsl:value-of select="Field01" /></Field01>
		<Field02><xsl:value-of select="Field02" /></Field02>
		<Field03></Field03>
		<Field04></Field04>
		<Field05></Field05>
		<Field06><xsl:value-of select="Field03" /></Field06>
		<Field07><xsl:value-of select="Field04" /></Field07>
		<Field08><xsl:value-of select="Field05" /></Field08>
		<Field09></Field09>
		<Field10></Field10>
		<xsl:for-each select="Field06">
			<Field11>
				<SubField1101><xsl:value-of select="SubField0601" /></SubField1101>
				<SubField1102><xsl:value-of select="SubField0602" /></SubField1102>
				<SubField1103><xsl:value-of select="SubField0603" /></SubField1103>
			</Field11>
		</xsl:for-each>
	</xsl:template>
	
</xsl:stylesheet>
