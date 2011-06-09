/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.oodt.cas.protocol.sftp;

//JUnit imports
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

import org.apache.oodt.cas.protocol.ProtocolFile;
import org.apache.oodt.cas.protocol.exceptions.ProtocolException;
import org.apache.oodt.cas.protocol.sftp.auth.HostKeyAuthentication;

import com.sshtools.daemon.SshDaemon;
import com.sshtools.daemon.configuration.XmlServerConfigurationContext;
import com.sshtools.j2ssh.configuration.ConfigurationException;
import com.sshtools.j2ssh.configuration.ConfigurationLoader;

import junit.framework.TestCase;

/**
 * Test class for {@link JschSftpProtocol}.
 * 
 * @author bfoster
 */
public class TestJschSftpProtocol extends TestCase {

	@Override
	public void setUp() {
    XmlServerConfigurationContext context = new XmlServerConfigurationContext();
    context.setServerConfigurationResource("src/testdata/server.xml");
    context.setPlatformConfigurationResource("src/testdata/platform.xml");
    try {
			ConfigurationLoader.initialize(false, context);
		} catch (ConfigurationException e1) {
			fail("Failed to initialize server configuration");
		}
    
    Executors.newSingleThreadExecutor().execute(new Runnable() {

			public void run() {
				try {
					SshDaemon.start();
				} catch (Exception e) {
					try { SshDaemon.stop(); } catch (Exception ignore) {}
					fail("Failed to start SSH daemon");
				}
			}
    	
    });
	}
	
	@Override
	public void tearDown() {
		try {
			SshDaemon.stop();
		} catch (IOException e) {
			fail("Failed to stop SSH daemon");
		}
	}
	
	public void testCDandPWDandLS() throws IOException, ProtocolException {
		JschSftpProtocol sftpProtocol = new JschSftpProtocol(2022);
		sftpProtocol.connect("localhost", new HostKeyAuthentication("bfoster", "", new File("src/testdata/sample-dsa.pub").getAbsoluteFile().getAbsolutePath()));
		ProtocolFile homeDir = sftpProtocol.pwd();
		ProtocolFile testDir = new ProtocolFile(homeDir, "sshTestDir", true);
		sftpProtocol.cd(testDir);
		assertEquals(testDir, sftpProtocol.pwd());
		List<ProtocolFile> lsResults = new ArrayList<ProtocolFile>(sftpProtocol.ls());
		assertEquals(1, lsResults.size());
		ProtocolFile testFile = lsResults.get(0);
		assertEquals(new ProtocolFile(testDir, "sshTestFile", false), testFile);
	}
	
}
