package com.cognifide.sling.query.selector;

import java.util.List;

import javax.jcr.Node;
import javax.jcr.RepositoryException;

import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cognifide.sling.query.api.ResourcePredicate;
import com.cognifide.sling.query.predicate.PropertyPredicate;

public class SelectorFilterPredicate implements ResourcePredicate {

	private static final Logger LOG = LoggerFactory.getLogger(SelectorFilterPredicate.class);

	private final String resourceType;

	private final List<PropertyPredicate> properties;

	public SelectorFilterPredicate(String resourceType, List<PropertyPredicate> properties) {
		this.resourceType = resourceType;
		this.properties = properties;
	}

	@Override
	public boolean accepts(Resource resource) {
		if (!isResourceType(resource, resourceType)) {
			return false;
		}
		for (PropertyPredicate predicate : properties) {
			if (!predicate.accepts(resource)) {
				return false;
			}
		}
		return true;
	}

	private static boolean isResourceType(Resource resource, String resourceType) {
		if (StringUtils.isBlank(resourceType)) {
			return true;
		}
		if (resource.isResourceType(resourceType)) {
			return true;
		}
		Node node = resource.adaptTo(Node.class);
		try {
			if (node != null) {
				return node.isNodeType(resourceType);
			}
		} catch (RepositoryException e) {
			LOG.error("Can't check node type", e);
		}
		return false;
	}
}
