<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<xml:output method="xml" />
	
	<xsl:template match="/">
		<StandardRecord>
			<Field01><xsl:value-of select="Org02Record/Field01" /></Field01>
			<Field02><xsl:value-of select="Org02Record/Field02" /></Field02>
			<Field03><xsl:value-of select="Org02Record/Field03" /></Field03>
			<Field04><xsl:value-of select="Org02Record/Field04" /></Field04>
			<Field05></Field05>
			<Field06><xsl:value-of select="Org02Record/Field05" /></Field06>
			<Field07><xsl:value-of select="Org02Record/Field06" /></Field07>
			<Field08><xsl:value-of select="Org02Record/Field07" /></Field08>
			<Field09></Field09>
			<Field10></Field10>
		</StandardRecord>
	</xsl:template>
	
</xsl:stylesheet>