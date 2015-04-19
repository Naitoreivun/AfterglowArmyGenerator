package skills;

public class BasicSkill implements Comparable<BasicSkill>
	{
		private final String name;
		private final String description;
		
		public static final BasicSkill SPECIALIST = new BasicSkill("Specjalista");
		public static final BasicSkill POINT_MAN = new BasicSkill("Zwiadowca");
		public static final BasicSkill SKIRMISHER = new BasicSkill("Harcownik");
		
		public BasicSkill()
		{
			name = "";
			description = "";
		};
		
		public BasicSkill(String name)
		{
			this.name = name.trim();
			description = "";
		};
		
		public BasicSkill(String name, String description)
		{
			this.name = name.trim();
			this.description = description.trim();
		}

		@Override
		public String toString()
		{
			return name;
		}

		@Override
		public final int hashCode()
		{
			final int prime = 31;
			int result = 1;
			result = prime * result + ((name == null) ? 0 : name.hashCode());
			return result;
		}

		@Override
		public final boolean equals(Object obj)
		{
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (!(obj instanceof BasicSkill))
				return false;
			BasicSkill other = (BasicSkill) obj;
			if (name == null)
			{
				if (other.name != null)
					return false;
			}
			else if (!name.equals(other.name))
				return false;
			return true;
		}
		
		@Override
		public int compareTo(BasicSkill other)
		{
			return name.compareToIgnoreCase(other.getName());
		}

		public String getName()
		{
			return name;
		}

		public String getDescription()
		{
			return description;
		}

	}