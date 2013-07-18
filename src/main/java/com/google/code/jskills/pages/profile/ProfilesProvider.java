package com.google.code.jskills.pages.profile;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.apache.wicket.extensions.markup.html.repeater.data.sort.SortOrder;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;

import com.google.code.jskills.business.services.ProfileService;
import com.google.code.jskills.domain.Profile;

/*
 * Data provider already extends a provider 
 * that implements ISortableDataProvider
 */

public class ProfilesProvider extends SortableDataProvider<Profile, String> {

	private static final long serialVersionUID = 1L;

	// @Inject
	private transient ProfileService profileService;

	private List<Profile> list;

	/*
	 * Define a default sort which will be used when the table is rendered
	 * before the user clicks any header of a sortable column
	 */

	public ProfilesProvider(ProfileService profileService) {
		this.profileService = profileService;
		setSort("id", SortOrder.ASCENDING);

		/*
		 * list.add(new Profile(1, "Profile1", "description1")); list.add(new
		 * Profile(2, "Profile2", "description2")); list.add(new Profile(3,
		 * "Profile3", "description3"));
		 */

		list = new ArrayList<Profile>(this.profileService.getAllProfiles());

	}

	class SortableDataProviderComparator implements Comparator<Profile>,
			Serializable {

		@Override
		public int compare(Profile o1, Profile o2) {
			PropertyModel<Comparable> model1 = new PropertyModel<Comparable>(
					o1, (String) getSort().getProperty());
			PropertyModel<Comparable> model2 = new PropertyModel<Comparable>(
					o2, (String) getSort().getProperty());

			int result = model1.getObject().compareTo(model2.getObject());

			if (!getSort().isAscending()) {
				result = -result;
			}

			return result;
		}

	}

	@Override
	public Iterator<? extends Profile> iterator(long first, long count) {
		// Get the data
		List<Profile> newList = new ArrayList<Profile>(
				profileService.getAllProfiles());

		// Sort the data
		SortableDataProviderComparator comparator = new SortableDataProviderComparator();
		Collections.sort(newList, comparator);

		// Return the data for the current page - this can be determined only
		// after sorting
		return newList.subList((int) first, (int) (first + count)).iterator();

	}

	@Override
	public long size() {
		return list.size();
	}

	@Override
	public IModel<Profile> model(final Profile profile) {
		return new AbstractReadOnlyModel<Profile>() {

			private static final long serialVersionUID = 1L;

			@Override
			public Profile getObject() {
				return (Profile) profile;
			}
		};
	}

}
