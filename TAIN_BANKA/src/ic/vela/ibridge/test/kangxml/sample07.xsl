<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xml:output method="xml" />

	<xsl:template match="/">
		<html lang="kr">
		<head>
		<title><xsl:value-of select="��ü/����" /></title>
		</head>
		<body><xsl:apply-templates select="��ü/����" /></body>
		</html>
	</xsl:template>
	
	<xsl:template match="��ü/����">
		<ol>
			<xsl:for-each select="����Ʈ/�׸�">
				<li><xsl:value-of select="name(.)" />:<xsl:value-of select="." /></li>
			</xsl:for-each>
		</ol>
	</xsl:template>

</xsl:stylesheet>
