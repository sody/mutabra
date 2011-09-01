/*
 * Copyright (c) 2008-2011 Ivan Khalopik.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.mutabra.web.components;

import com.mutabra.web.base.components.AbstractComponent;
import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.PropertyOverrides;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SupportsInformalParameters;

/**
 * @author Ivan Khalopik
 */
@SupportsInformalParameters
public class Panel extends AbstractComponent {

	@Property
	@Parameter(defaultPrefix = BindingConstants.LITERAL)
	private String title;

	@Property
	@Parameter(required = true, allowNull = false, defaultPrefix = BindingConstants.LITERAL)
	private String[] buttons;

	@Property
	@Parameter(name = "class", defaultPrefix = BindingConstants.LITERAL)
	private String className;

	@Property
	@Parameter(value = "this", allowNull = false)
	private PropertyOverrides overrides;

}
