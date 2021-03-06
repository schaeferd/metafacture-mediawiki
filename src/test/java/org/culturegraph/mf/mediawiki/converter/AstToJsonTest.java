/*
 *  Copyright 2013 Deutsche Nationalbibliothek
 *
 *  Licensed under the Apache License, Version 2.0 the "License";
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.culturegraph.mf.mediawiki.converter;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;

import org.culturegraph.mf.mediawiki.converter.WikiTextParser.ParseLevel;
import org.culturegraph.mf.mediawiki.type.WikiPage;
import org.culturegraph.mf.stream.pipe.ObjectBuffer;
import org.culturegraph.mf.util.ResourceUtil;
import org.junit.Test;

/**
 * Test {@link AstToJson}.
 * 
 * @author Christoph Böhme
 *
 */
public final class AstToJsonTest {

	private static final long PAGEID_BIRMINGHAM = 57252L;
	private static final long REVISIONID_BIRMINGHAM = 105226552L;
	private static final String URL_BIRMINGHAM = "http://de.wikipedia.org/wiki/Birmingham";
	private static final String TITLE_BIRMINGHAM = "Birmingham";
	private static final String WIKITEXT_FILE_BIRMINGHAM = "wikitext/birmingham.txt";

	@Test
	public void testAstToJson() throws IOException {
		final WikiTextParser wikiPageParser = new WikiTextParser();
		wikiPageParser.setParseLevel(ParseLevel.POSTPROCESS);
		final AstToJson astToJson = new AstToJson();
		final ObjectBuffer<WikiPage> result = new ObjectBuffer<WikiPage>();
		
		wikiPageParser.setReceiver(astToJson)
				.setReceiver(result);
		
		final WikiPage page = new WikiPage();
		page.setPageId(PAGEID_BIRMINGHAM);
		page.setRevisionId(REVISIONID_BIRMINGHAM);
		page.setUrl(URL_BIRMINGHAM);
		page.setTitle(TITLE_BIRMINGHAM);
		page.setWikiText(ResourceUtil.loadTextFile(WIKITEXT_FILE_BIRMINGHAM));
		page.setJsonAst(null);

		wikiPageParser.process(page);
		wikiPageParser.closeStream();

		assertNotNull(result.pop().getJsonAst());
		
		// NOTE: This test does not check whether the output of the AstToJson
		// module is a valid JSON representation of the AST. It merely checks
		// that something is generated.
	}

}
