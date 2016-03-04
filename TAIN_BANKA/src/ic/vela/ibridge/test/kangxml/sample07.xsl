<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xml:output method="xml" />

	<xsl:template match="/">
		<html lang="kr">
		<head>
		<title><xsl:value-of select="전체/제목" /></title>
		</head>
		<body><xsl:apply-templates select="전체/본문" /></body>
		</html>
	</xsl:template>
	
	<xsl:template match="전체/본문">
		<ol>
			<xsl:for-each select="리스트/항목">
				<li><xsl:value-of select="name(.)" />:<xsl:value-of select="." /></li>
			</xsl:for-each>
		</ol>
	</xsl:template>

</xsl:stylesheet>
