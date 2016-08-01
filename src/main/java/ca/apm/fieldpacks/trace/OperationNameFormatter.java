package ca.apm.fieldpacks.trace;

import ca.apm.fieldpacks.utils.FieldPackUtils;
import ca.apm.fieldpacks.utils.Logger;

import java.util.Set;

import com.wily.introscope.agent.IAgent;
import com.wily.introscope.agent.trace.INameFormatter;
import com.wily.introscope.agent.trace.InvocationData;
import com.wily.util.StringUtils;
import com.wily.util.feedback.IModuleFeedbackChannel;

/**
 * MongoDB Name Formatter implementation
 * 
 * @author Miguel Fernandes (@ca.com)
 *
 */
public class OperationNameFormatter implements INameFormatter {

	protected IModuleFeedbackChannel log;

	/**
	 * These placeholders are used in the metric name in the pbd file and replaced
	 * by the name formatter.
	 */
	public static final String COLLECTION_NAME_HOLDER = "{collection}";
	public static final String QUERY_HOLDER = "{query}";

	/**
	 * Create new name formatter.
	 * 
	 * @param agent
	 *            the agent
	 */
	public OperationNameFormatter(IAgent agent) {
		this.log = Logger.getLogger(agent);
	}

	/**
	 * Do the name formatting.
	 * 
	 * @param name
	 *            the metric path as stated in the pbd file
	 * @param data
	 *            the invocation data containing all the state of the method
	 *            that the associated tracer is invoked on.
	 * @return the metric path that is sent from the agent to the Enterprise
	 *         Manager All custom place holders should be replaced by the name
	 *         formatter.
	 */
	public String INameFormatter_format(String name, InvocationData data) {
		// call getXXX() to do the real work and replace it
		// best practice is to use one method per place holder
		name = StringUtils.replace(name, COLLECTION_NAME_HOLDER, getCollectionName(data));
		name = StringUtils.replace(name, QUERY_HOLDER, getQuery(data));

		// make sure there are only allowed characters in the string
		name = FieldPackUtils.formatToBeResourceSafe(name);

		return name;
	}

	/**
	 * This is where all the dirty work (reflection calls) gets done for getting the collection.
	 * 
	 * @param data
	 *            the invocation data
	 * @return the string to replace the place holder with
	 */

	public String getCollectionName(InvocationData data) {

		String collection = "<unknown>";

		try {
			Object invocationObject = data.getInvocationObject();
			if (null != invocationObject) {

				Object collectionNameObject = FieldPackUtils.invokeMethodOnObject(invocationObject, "getName");

				if ((null != collectionNameObject) && (collectionNameObject instanceof String)) {
					collection = (String) collectionNameObject;

					// now cache it
					data.put(COLLECTION_NAME_HOLDER, collection);
				}
			}
		} catch (Exception e) {
			// Do nothing
			// log.error("error in getCollectionName(): " + e.getMessage());
			// log.debug("error in getCollectionName(): " + e);
		}
		return collection;
	}

	/**
	 * This is where all the dirty work (reflection calls) gets done for getting the query.
	 * 
	 * @param data
	 *            the invocation data
	 * @return the string to replace the place holder with
	 */
	@SuppressWarnings("unchecked")
	public String getQuery(InvocationData data) {

		String query = "<unknown>";

		try {

			// is it already cached in the invocation data object?

			Object queryKeysObject = data.get(QUERY_HOLDER);
			if (queryKeysObject != null) {
				return (String) queryKeysObject;
			}

			Object parameterObject = data.getInvocationParameterAsObject(0);

			if (null != parameterObject) {

				// always use these safe convenience methods for reflection
				// calls
				queryKeysObject = FieldPackUtils.invokeMethodOnObject(parameterObject, "keySet");

				if ((null != queryKeysObject) && (queryKeysObject instanceof Set<?>)) {

					String queryObject = "{";
					for (String firstLevelKey : (Set<String>) queryKeysObject) {
						if (!queryObject.equals("{")) {
							queryObject += ",";
						}
						queryObject += " " + firstLevelKey + " = ? ";
					}

					queryObject += "}";

					query = queryObject;

					// now cache it
					data.put(QUERY_HOLDER, query);
				}
			}

		} catch (Exception e) {
			// Do nothing
			// log.error("error in getCollectionName(): " + e.getMessage());
			// log.debug("error in getCollectionName(): " + e);
		}

		return query;
	}
}
