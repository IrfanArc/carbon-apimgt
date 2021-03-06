package org.wso2.carbon.apimgt.gateway.internal;
/*
 * Copyright (c) 2016, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wso2.carbon.apimgt.gateway.analytics.AnalyticsConfiguration;
//import org.wso2.carbon.kernel.configprovider.ConfigProvider;

/**
 * Class used to activate configuration loading
 * TODO refactor class when kernal is updated to 5.2.0
 */
public class ConfigurationActivator {
    private static Logger log = LoggerFactory.getLogger(ConfigurationActivator.class);

    /**
     * Get the ConfigProvider service.
     * This is the bind method that gets called for ConfigProvider service registration that satisfy the policy.
     *
     * @param configProvider the ConfigProvider service that is registered as a service.
     */
    @Reference(name = "carbon.config.provider", service = ConfigProvider.class,
            cardinality = ReferenceCardinality.MANDATORY, policy = ReferencePolicy.DYNAMIC,
            unbind = "unregisterConfigProvider")
    protected void registerConfigProvider(ConfigProvider configProvider) {
        ServiceReferenceHolder.getInstance().setConfigProvider(configProvider);

        /*try {
           AnalyticsConfiguration analyticsConfiguration = ServiceReferenceHolder.getInstance().getConfigProvider()
                    .getConfigurationObject(AnalyticsConfiguration.class);
        } catch (CarbonConfigurationException e) {
            log.error("error getting config", e);
        }*/

        AnalyticsConfiguration analyticsConfiguration = new AnalyticsConfiguration();
        log.info("Setting default analytics configurations");
        log.debug("Analytics enabled = " + analyticsConfiguration.isEnabled());
        ServiceReferenceHolder.getInstance().setAnalyticsConfiguration(analyticsConfiguration);
    }

    /**
     * This is the unbind method for the above reference that gets called for ConfigProvider instance un-registrations.
     *
     * @param configProvider the ConfigProvider service that get unregistered.
     */
    protected void unregisterConfigProvider(ConfigProvider configProvider) {
        ServiceReferenceHolder.getInstance().setConfigProvider(null);
        ServiceReferenceHolder.getInstance().setAnalyticsConfiguration(null);
    }
}
