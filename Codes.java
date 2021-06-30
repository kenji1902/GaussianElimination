public class Codes {
    public String part1 = "<p class=MsoNormal style='margin-top:0in;line-height:normal;background:#2B2B2B'><span\n" +
            "style='font-family:Consolas;color:gray'>//get the\n" +
            "Highest Value in Row</span><span style='font-family:Consolas;\n" +
            "color:#A9B7C6'><br>\n" +
            "</span><span style='font-family:Consolas;color:white'>for (int\n" +
            "i = k + 1; i &lt; Unknowns; i++) {<br>\n" +
            "    if (Math.<i>abs</i>(</span><span style='font-family:Consolas;\n" +
            "color:#FB800E'>mat[i][k]</span><span style='font-family:Consolas;\n" +
            "color:white'>) &gt; value_max) {<br>\n" +
            "        value_max = (int) </span><span style='font-family:\n" +
            "Consolas;color:#FB800E'>mat[i][k]</span><span style='\n" +
            "font-family:Consolas;color:white'>;<br>\n" +
            "        index_max = i;<br>\n" +
            "    }</span><span style='font-family:Consolas;color:#CC7832'><br>\n" +
            "</span><span style='font-family:Consolas;color:#A9B7C6'>}</span></p>";

    public String part2 = "<pre style='background:#2B2B2B'><span style='font-family:Consolas;color:white'>if (</span><span\n" +
            "style='font-family:Consolas;color:#FB800E'>mat[k][index_max] </span><span\n" +
            "style='font-family:Consolas;color:white'>== 0) {<br>\n" +
            "    return k;<br>\n" +
            "}</span></pre>";

    public String part3 = "<pre style='background:#2B2B2B'><span style='font-family:Consolas;color:white'>for (int k = 0; k &lt;= Unknowns; k++) {<br>\n" +
            "    double temp = </span><span style='font-family:Consolas;color:#FB800E'>mat[i][k]</span><span\n" +
            "style='font-family:Consolas;color:white'>;<br>\n" +
            "    </span><span style='font-family:Consolas;color:#FB800E'>mat[i][k] </span><span\n" +
            "style='font-family:Consolas;color:white'>= </span><span style='font-family:\n" +
            "Consolas;color:#FF002F'>mat[j][k]</span><span style='font-family:Consolas;\n" +
            "color:white'>;<br>\n" +
            "    </span><span style='font-family:Consolas;color:#FF002F'>mat[j][k] </span><span\n" +
            "style='font-family:Consolas;color:white'>= temp;<br>\n" +
            "}</span></pre>";

    public String part4 = "<body lang=EN-AU style='tab-interval:.5in;word-wrap:break-word'>\n" +
            "\n" +
            "<div class=WordSection1><pre style='background:#2B2B2B'><span style='font-family:\n" +
            "Consolas;color:white;mso-themecolor:background1'>for (int i = k + 1; i &lt; Unknowns; i++) {<br>\n" +
            "<span style='mso-spacerun:yes'> </span><span style='mso-spacerun:yes'>   </span></span><span\n" +
            "style='font-family:Consolas;color:#AFABAB;mso-themecolor:background2;\n" +
            "mso-themeshade:191;mso-style-textfill-fill-color:#AFABAB;mso-style-textfill-fill-themecolor:\n" +
            "background2;mso-style-textfill-fill-alpha:100.0%;mso-style-textfill-fill-colortransforms:\n" +
            "lumm=75000'>//Create a multiplier for equation<br>\n" +
            "<span style='mso-spacerun:yes'>    </span>//to make the lower triangle to be zero<br>\n" +
            "</span><span style='font-family:Consolas;color:white;mso-themecolor:background1'><span style='mso-spacerun:yes'>    </span>double Multiplier = </span><span\n" +
            "style='font-family:Consolas;color:#4043F5'>mat[i][k] </span><span\n" +
            "style='font-family:Consolas;color:white;mso-themecolor:background1'>/ </span><span\n" +
            "style='font-family:Consolas;color:#FFC300'>mat[k][k]</span><span\n" +
            "style='font-family:Consolas;color:white;mso-themecolor:background1'>;<br>\n" +
            "<span style='mso-spacerun:yes'>    </span>for (int j = k + 1; j &lt;= Unknowns; j++) {<br>\n" +
            "<span style='mso-spacerun:yes'>        </span></span><span style='font-family:\n" +
            "Consolas;color:#FB800E'>mat[i][j] </span><span style='font-family:Consolas;\n" +
            "color:white;mso-themecolor:background1'>-= </span><span style='font-family:\n" +
            "Consolas;color:#FF002F'>mat[k][j]</span><span style='font-family:Consolas;\n" +
            "color:white;mso-themecolor:background1'> * Multiplier;<br>\n" +
            "<span style='mso-spacerun:yes'>    </span>}<br>\n" +
            "<span style='mso-spacerun:yes'>    </span></span><span style='font-family:Consolas;\n" +
            "color:#AFABAB;mso-themecolor:background2;mso-themeshade:191;mso-style-textfill-fill-color:\n" +
            "#AFABAB;mso-style-textfill-fill-themecolor:background2;mso-style-textfill-fill-alpha:\n" +
            "100.0%;mso-style-textfill-fill-colortransforms:lumm=75000'>//set the Current lower triangle to Zero<br>\n" +
            "</span><span style='font-family:Consolas;color:white;mso-themecolor:background1'><span style='mso-spacerun:yes'>    </span>mat[i][k] = 0;<br>\n" +
            "}</span><span style='color:#A9B7C6'></span></pre></div>\n" +
            "\n" +
            "</body>";
    public String part5 = "<body lang=EN-AU style='tab-interval:.5in;word-wrap:break-word'>\n" +
            "\n" +
            "<div class=WordSection1><pre style='background:#2B2B2B'><span style='font-family:\n" +
            "Consolas;color:white;mso-themecolor:background1'>for (int i = k + 1; i &lt; Unknowns; i++) {<br>\n" +
            "<span style='mso-spacerun:yes'>    </span></span><span style='font-family:Consolas;\n" +
            "color:#AFABAB;mso-themecolor:background2;mso-themeshade:191;mso-style-textfill-fill-color:\n" +
            "#AFABAB;mso-style-textfill-fill-themecolor:background2;mso-style-textfill-fill-alpha:\n" +
            "100.0%;mso-style-textfill-fill-colortransforms:lumm=75000'>//Create a multiplier for equation<br>\n" +
            "<span style='mso-spacerun:yes'>    </span>//to make the lower triangle to be zero<br>\n" +
            "</span><span style='font-family:Consolas;color:white;mso-themecolor:background1'><span style='mso-spacerun:yes'>    </span>double Multiplier = mat[i][k] / mat[k][k];<br>\n" +
            "<span style='mso-spacerun:yes'>    </span>for (int j = k + 1; j &lt;= Unknowns; j++) {<br>\n" +
            "<span style='mso-spacerun:yes'>        </span>mat[i][j] -= mat[k][j] * Multiplier;<br>\n" +
            "<span style='mso-spacerun:yes'>    </span>}<br>\n" +
            "<span style='mso-spacerun:yes'>    </span></span><span style='font-family:Consolas;\n" +
            "color:#AFABAB;mso-themecolor:background2;mso-themeshade:191;mso-style-textfill-fill-color:\n" +
            "#AFABAB;mso-style-textfill-fill-themecolor:background2;mso-style-textfill-fill-alpha:\n" +
            "100.0%;mso-style-textfill-fill-colortransforms:lumm=75000'>//set the Current lower triangle to Zero<br>\n" +
            "</span><span style='font-family:Consolas;color:white;mso-themecolor:background1'><span style='mso-spacerun:yes'>    </span></span><span\n" +
            "style='font-family:Consolas;color:#4043F5'>mat[i][k] </span><span\n" +
            "style='font-family:Consolas;color:white;mso-themecolor:background1'>= 0;<br>\n" +
            "}</span><span style='color:#A9B7C6'></span></pre>\n" +
            "\n" +
            "<p class=MsoNormal><span style='color:#4043F5'></span></p>\n" +
            "\n" +
            "</div>\n" +
            "\n" +
            "</body>";

    public String part6 = "<pre style='background:#2B2B2B'><span style='font-family:Consolas;color:white'>for (int i = Unknowns - 1; i &gt;= 0; i--) {<br>\n" +
            "      </span><span style='font-family:Consolas;color:#FF002F'>Solution[i]</span><span\n" +
            "style='font-family:Consolas;color:white'> = </span><span style='font-family:\n" +
            "Consolas;color:#FB800E'>mat[i][Unknowns]</span><span style='font-family:Consolas;\n" +
            "color:white'>;<br>\n" +
            "      for (int j = i + 1; j &lt; Unknowns; j++) {<br>\n" +
            "          Solution[i] -= mat[i][j] * Solution[j];<br>\n" +
            "      }<br>\n" +
            "      Solution[i] = Solution[i] / mat[i][i];<br>\n" +
            "  }</span></pre>";

    public String part7 = "<pre style='background:#2B2B2B'><span style='font-family:Consolas;color:white'>for (int i = Unknowns - 1; i &gt;= 0; i--) {<br>\n" +
            "      Solution[i] = mat[i][Unknowns];<br>\n" +
            "      for (int j = i + 1; j &lt; Unknowns; j++) {<br>\n" +
            "          </span><span style='font-family:Consolas;color:#FF002F'>Solution[i] </span><span\n" +
            "style='font-family:Consolas;color:white'>-= </span><span style='font-family:\n" +
            "Consolas;color:#FB800E'>mat[i][j]</span><span style='font-family:Consolas;\n" +
            "color:white'> * </span><span style='font-family:Consolas;color:#FFC300'>Solution[j];</span><span\n" +
            "style='font-family:Consolas;color:white'><br>\n" +
            "      }<br>\n" +
            "      Solution[i] = Solution[i] / mat[i][i];<br>\n" +
            "  }</span></pre>";

    public String part8 = "<pre style='background:#2B2B2B'><span style='font-family:Consolas;color:white'>for (int i = Unknowns - 1; i &gt;= 0; i--) {<br>\n" +
            "      Solution[i] = mat[i][Unknowns];<br>\n" +
            "      for (int j = i + 1; j &lt; Unknowns; j++) {<br>\n" +
            "          Solution[i] -= mat[i][j] * Solution[j];<br>\n" +
            "      }<br>\n" +
            "      </span><span style='font-family:Consolas;color:#FF002F'>Solution[i] </span><span\n" +
            "style='font-family:Consolas;color:white'>= </span><span style='font-family:\n" +
            "Consolas;color:#FF002F'>Solution[i] </span><span style='font-family:Consolas;\n" +
            "color:white'>/ </span><span style='font-family:Consolas;color:#FB800E'>mat[i][i]</span><span\n" +
            "style='font-family:Consolas;color:white'>;<br>\n" +
            "  }</span></pre>";

}
