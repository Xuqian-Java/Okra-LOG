/*
 *     Copyright 2016-2026 TinyZ
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.ogcs.log;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ogcs.log.config.OkraConfig;
import org.ogcs.log.config.OkraProperties;
import org.ogcs.log.core.MissionBoard;
import org.ogcs.log.core.server.UdpLogServer;
import org.ogcs.log.serlvet.ApiServer;

/**
 * @author TinyZ
 * @date 2016-07-03.
 */
public class Bootstrap {

    private static final Logger LOG = LogManager.getLogger(Bootstrap.class);

    public static void main(String[] args) {
        LOG.info("Bootstrap Okra-LOG ...");
        UdpLogServer server = null;
        ApiServer apiServer = null;
        try {
            OkraConfig config = OkraProperties.getConfig();
            MissionBoard missionBoard = new MissionBoard(config);
            missionBoard.init();

            server = new UdpLogServer(config.getPort(), missionBoard);
            server.start();

            apiServer = new ApiServer(9006);
            apiServer.start();
            LOG.info("Okra-LOG bootstrap success.");
        } catch (Exception e) {
            if (server != null)
                server.stop();
            if (apiServer != null)
                apiServer.stop();
            LOG.info("Okra-LOG bootstrap failure.", e);
        }
    }
}
