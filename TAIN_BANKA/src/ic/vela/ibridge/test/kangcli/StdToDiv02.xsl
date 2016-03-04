<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<xml:output method="xml" />
	
	<xsl:template match="/">
		<Org02Record>
			<Field01><xsl:value-of select="StandardRecord/Field01" /></Field01>
			<Field02><xsl:value-of select="StandardRecord/Field02" /></Field02>
			<Field03><xsl:value-of select="StandardRecord/Field03" /></Field03>
			<Field04><xsl:value-of select="StandardRecord/Field04" /></Field04>
			<Field05><xsl:value-of select="StandardRecord/Field06" /></Field05>
			<Field06><xsl:value-of select="StandardRecord/Field07" /></Field06>
			<Field07><xsl:value-of select="StandardRecord/Field08" /></Field07>
		</Org02Record>
	</xsl:template>
	
</xsl:stylesheet>