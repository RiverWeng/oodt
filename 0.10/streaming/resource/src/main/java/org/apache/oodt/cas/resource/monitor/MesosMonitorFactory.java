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

package org.apache.oodt.cas.resource.monitor;

//JDK imports
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author starchmd
 * @version $Revision$
 *
 * <p>
 * Creates implementations of {@link MesosMonitor}s.
 * </p>
 *
 */
public class MesosMonitorFactory implements MonitorFactory {

    private static final Logger LOG = Logger
            .getLogger(MesosMonitorFactory.class.getName());

    /*
     * (non-Javadoc)
     *
     * @see
     * gov.nasa.jpl.oodt.cas.resource.monitor.MonitorFactory#createMonitor()
     */
    public MesosMonitor createMonitor() {
        try {
            return new MesosMonitor();
        } catch (Exception e) {
            LOG.log(Level.SEVERE, "Failed to create Mesos Monitor : " + e.getMessage(), e);
            return null;
        }
    }

}
